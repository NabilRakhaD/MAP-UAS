package com.findurgimmy.umn.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    TextView signIn, btnLets;
    EditText username, email, phone, pass;
    ProgressDialog progressDialog;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String radioButtonText;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.Phonenum);
        pass = findViewById(R.id.pass);
        radioGroup = findViewById(R.id.radiogrup);
        signIn = findViewById(R.id.SignIn);
        btnLets = findViewById(R.id.btnLets);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Tunggu Sebentar");
        progressDialog.setCancelable(false);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MoveToSignIn = new Intent(SignUpActivity.this, signIn_Activity.class);
                startActivity(MoveToSignIn);
            }
        });

        btnLets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int SelectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(SelectedId);

                if(username.getText().length()>0 && email.getText().length()>0 && pass.getText().length()>0 && phone.getText().length()>0 && SelectedId != -1){
                    radioButtonText = radioButton.getText().toString();
                    register(username.getText().toString(), email.getText().toString(), pass.getText().toString(), phone.getText().toString(), radioButtonText);
                    Toast.makeText(SignUpActivity.this, "Berhasil Masuk", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SignUpActivity.this, "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void register(String username, String email, String pass, String phone, String gender) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() && task.getResult()!=null){
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    Log.d("uid", firebaseUser.getUid());
                    if(firebaseUser!=null){
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build();
                        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                reload();
                            }
                        });
                    }else{
                        Toast.makeText(SignUpActivity.this, "Sign Up Gagal", Toast.LENGTH_SHORT).show();
                    }

                    Map<String, Object> user = new HashMap<>();
                    user.put("Nama", username);
                    user.put("Jenis Kelamin", gender);
                    user.put("PhoneNumber", phone);
                    user.put("Membership", "Belum mempunyai membership");
                    user.put("Personal Trainer", "Belum mempunyai personal trainer");

                    db.collection("User").document(firebaseUser.getUid())
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("TAG", "Error writing document", e);
                                }
                            });

                } else{
                    Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private  void reload(){
        Intent MainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(MainActivity);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
    
}
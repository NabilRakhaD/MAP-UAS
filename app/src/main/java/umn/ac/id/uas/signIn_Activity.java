package umn.ac.id.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class signIn_Activity extends AppCompatActivity {
    TextView signUp, btnReady, title, email, password;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Boolean isGoogle;
    ProgressDialog progressDialog;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    GoogleSignInAccount acct = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signUp = findViewById(R.id.SignUp);
        btnReady = findViewById(R.id.btnReady);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(signIn_Activity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Tunggu Sebentar");
        progressDialog.setCancelable(false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        String username = intent.getStringExtra("Nama");
        String gender = intent.getStringExtra("Gender");
        String phonenum = intent.getStringExtra("PhoneNum");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MoveToSignUp = new Intent(signIn_Activity.this, SignUpActivity.class);
                startActivity(MoveToSignUp);
            }
        });

        btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().length()>0 && password.getText().length()>0){
                    login(email.getText().toString(), password.getText().toString());
                }else{
                    Toast.makeText(signIn_Activity.this, "Isi semua data terlebih dahulu!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView loginWithGoogleButton = findViewById(R.id.Google);

        loginWithGoogleButton.setOnClickListener(v -> {
            loginWithGoogle();
        });
    }

    private void login(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() && task.getResult()!=null){
                    if(task.getResult().getUser()!=null){
                        reload();
                    }else{
                        Toast.makeText(signIn_Activity.this, "Sign In Gagal", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(signIn_Activity.this, "Sign In Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private  void reload(){
        isGoogle = false;
        Intent MoveToMainActivity2 = new Intent(this,MainActivity.class);
        MoveToMainActivity2.putExtra("isGoogle", isGoogle);
        startActivity(MoveToMainActivity2);
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

    private void loginWithGoogle() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                acct = GoogleSignIn.getLastSignedInAccount(this);
                isGoogle = true;
                Intent MoveToMainActivity2 = new Intent(this,MainActivity.class);
                MoveToMainActivity2.putExtra("isGoogle", isGoogle);
                startActivity(MoveToMainActivity2);

                Toast.makeText(this, "Welcome, " + acct.getDisplayName(), Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
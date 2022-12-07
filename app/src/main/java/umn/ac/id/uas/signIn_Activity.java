package umn.ac.id.uas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class signIn_Activity extends AppCompatActivity {
    TextView signUp, btnReady, email, password;
    private FirebaseAuth mAuth;
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

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("61956869598-6eieomiljbdgu84ntjqvd13nk4kd4i93.apps.googleusercontent.com")
                .requestEmail().build();
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
                //acct = GoogleSignIn.getLastSignedInAccount(this);
                acct = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(acct.getIdToken());
                isGoogle = true;

                Toast.makeText(this, "Welcome, " + acct.getDisplayName(), Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(TextUtils.isEmpty(user.getUid())){
                                Log.d("loguid", "ga ada uid");
                            }else{
                                Log.d("loguid", user.getUid());
                            }

                                Map<String, Object> userGoogle = new HashMap<>();
                                userGoogle.put("Nama", "-");
                                userGoogle.put("Jenis Kelamin", "-");
                                userGoogle.put("PhoneNumber", "-");

                                db.collection("User").document(user.getUid())
                                        .set(userGoogle)
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
                            isGoogle = true;
                            Intent MoveToMainActivity2 = new Intent(getApplicationContext(),MainActivity.class);
                            MoveToMainActivity2.putExtra("isGoogle", isGoogle);
                            startActivity(MoveToMainActivity2);
                        }
                    }
                });
    }

    private void isiDataGoogle() {

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case REQ_ONE_TAP:
//                try {
//                    SignInCredential googleCredential = oneTapClient.getSignInCredentialFromIntent(data);
//                    String idToken = googleCredential.getGoogleIdToken();
//                    if (idToken !=  null) {
//                        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
//                        mAuth.signInWithCredential(firebaseCredential)
//                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        if (task.isSuccessful()) {
//                                            // Sign in success, update UI with the signed-in user's information
//                                            Log.d("TAG", "signInWithCredential:success");
//                                            FirebaseUser user = mAuth.getCurrentUser();
//                                            reload();
//                                        } else {
//                                            // If sign in fails, display a message to the user.
//                                            Log.w("TAG", "signInWithCredential:failure", task.getException());
//                                            reload();
//                                        }
//                                    }
//                                });
//                    }
//                } catch (ApiException e) {
//                    // ...
//                }
//                break;
//        }
//    }
}



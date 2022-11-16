package umn.ac.id.uas;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import umn.ac.id.uas.retrofit.APIService;

public class signIn_Activity extends AppCompatActivity {
    TextView signUp, btnReady, title;
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

        signUp = findViewById(R.id.SignUp);
        btnReady = findViewById(R.id.btnReady);

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
                Intent MoveToMainActivity = new Intent(signIn_Activity.this, MainActivity.class);
                startActivity(MoveToMainActivity);
            }
        });

        TextView loginWithGoogleButton = findViewById(R.id.Google);

        loginWithGoogleButton.setOnClickListener(v -> {
            loginWithGoogle();
        });
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
                Intent MoveToMainActivity2 = new Intent(this,MainActivity.class);
                startActivity(MoveToMainActivity2);

                Toast.makeText(this, "Welcome, " + acct.getDisplayName(), Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
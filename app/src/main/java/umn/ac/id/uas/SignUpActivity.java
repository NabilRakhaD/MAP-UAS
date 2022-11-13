package umn.ac.id.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {
    TextView signIn, btnLets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        signIn = findViewById(R.id.SignIn);
        btnLets = findViewById(R.id.btnLets);

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
                Intent MoveToMainActivity = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(MoveToMainActivity);
            }
        });
    }


}
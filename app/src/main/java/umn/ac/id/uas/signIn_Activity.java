package umn.ac.id.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import umn.ac.id.uas.retrofit.APIService;
import umn.ac.id.uas.retrofit.UserModel;

public class signIn_Activity extends AppCompatActivity {
    TextView signUp, btnReady, title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        APIService.endpoint().getUser().enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    List<UserModel.User> user = response.body().getUsers();
                    Log.i("tes", user.get(0).getFull_name());
                }
                Log.i("set2", "gagal");
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.i("tes", t.getMessage());
            }
        });

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
    }
}
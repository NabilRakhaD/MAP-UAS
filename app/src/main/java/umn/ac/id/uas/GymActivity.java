package umn.ac.id.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GymActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }
}
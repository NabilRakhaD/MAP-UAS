package umn.ac.id.uas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView searchpic, homepic, profilepic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        searchpic = findViewById(R.id.searchLogo);
        homepic = findViewById(R.id.homeLogo);
        profilepic = findViewById(R.id.profileLogo);

        searchpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchGymPT();
            }
        });

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MoveToProfile = new Intent(MainActivity.this, ProfilActivity.class);
                startActivity(MoveToProfile);
            }
        });
    }

    protected void searchGymPT(){
        final Dialog search = new Dialog(MainActivity.this);

        search.requestWindowFeature(Window.FEATURE_NO_TITLE);
        search.setContentView(R.layout.search_dialog);
        search.setCancelable(true);

        Window window = search.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);

        ConstraintLayout constraintGym = search.findViewById(R.id.constraintGym);
        ConstraintLayout constraintPT = search.findViewById(R.id.constraintPT);

        constraintGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MoveToGymList = new Intent(MainActivity.this, GymActivity.class );
                startActivity(MoveToGymList);
            }
        });

        constraintPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MoveToPTList = new Intent(MainActivity.this, PTActivity.class);
                startActivity(MoveToPTList);
            }
        });

        search.show();
    }
}
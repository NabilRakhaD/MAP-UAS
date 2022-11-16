package umn.ac.id.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class GymActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListGymAdapter gymadap;
    public ArrayList<Gym>listgym = new ArrayList<>();

    public void dataGym(){
        listgym.add(new Gym("Gold Gym", "Gading Serpong", "200 review", "0.5 km away",
                "Fitness, Yoga", R.drawable.goldgym, 5, 30000, 50000));
        listgym.add(new Gym("HotShape Gym", "Gading Serpong 2", "300 review", "0.5 km away",
                "Fitness, Zumba",R.drawable.hotshape, 4, 30000, 50000));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);
        dataGym();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        gymadap = new ListGymAdapter(this, listgym);
        recyclerView.setAdapter(gymadap);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView btmNavView = findViewById(R.id.btmNavigationView);

        btmNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        searchGymPT();
                        break;

                    case R.id.home:
                        Intent MoveToHome = new Intent(GymActivity.this, MainActivity.class);
                        startActivity(MoveToHome);
                        break;

                    case R.id.profile:
                        Intent MoveToProfile = new Intent(GymActivity.this, ProfilActivity.class);
                        startActivity(MoveToProfile);
                        break;
                }
                return true;
            }
        });

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    protected void searchGymPT(){
        final Dialog search = new Dialog(GymActivity.this);

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
                Intent MoveToGymList = new Intent(GymActivity.this, GymActivity.class );
                startActivity(MoveToGymList);
            }
        });

        constraintPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MoveToPTList = new Intent(GymActivity.this, PTActivity.class);
                startActivity(MoveToPTList);
            }
        });

        search.show();
    }

}
package umn.ac.id.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView HomeTitle;
    ViewPager viewPager;
    ArrayList<Gym> listgymMain = new ArrayList<>();
    MainGymAdapter maingymadap;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        HomeTitle = findViewById(R.id.HomeTitle);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent =  getIntent();
        boolean isGoogle = intent.getBooleanExtra("isGoogle", false);

        if(isGoogle){
            String name = GoogleSignIn.getLastSignedInAccount(this).getDisplayName();
            HomeTitle.setText("Go Gym, " + name.split(" ", 3 )[0]);
            Toast.makeText(this, "masuk gugel", Toast.LENGTH_SHORT).show();
        }else{
            if(firebaseUser!=null){
                HomeTitle.setText(firebaseUser.getDisplayName());
                Toast.makeText(this, "masuk firebase", Toast.LENGTH_SHORT).show();
            }
        }

        viewPager = findViewById(R.id.viewpager);
        dataGym();
        maingymadap = new MainGymAdapter(this, listgymMain);
        viewPager.setAdapter(maingymadap);


        BottomNavigationView btmNavView = findViewById(R.id.btmNavigationView);

        btmNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        searchGymPT();
                        break;

                    case R.id.home:
                        break;

                    case R.id.profile:
                        Intent MoveToProfile = new Intent(MainActivity.this, ProfilActivity.class);
                        startActivity(MoveToProfile);
                        break;
                }
                return true;
            }
        });

    }

    public void dataGym(){
        listgymMain.add(new Gym("Gold Gym", "Gading Serpong", "200 review", "0.5 km away",
                "Fitness, Yoga", R.drawable.goldgym, 5, 30000, 50000));
        listgymMain.add(new Gym("HotShape Gym", "Gading Serpong 2", "300 review", "0.5 km away",
                "Fitness, Zumba",R.drawable.hotshape, 4, 30000, 50000));
        listgymMain.add(new Gym("Progenex Gym", "Gading Serpong 2", "300 review", "0.5 km away",
                "Fitness, Zumba",R.drawable.progenex, 4, 30000, 50000));
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                searchGymPT();
                break;
            case R.id.home:
                break;

            case R.id.profile:
                Intent MoveToProfile = new Intent(MainActivity.this, ProfilActivity.class);
                startActivity(MoveToProfile);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
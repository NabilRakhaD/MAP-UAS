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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class PTActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListPTAdapter ptadap;
    public ArrayList<PT> listpt = new ArrayList<>();
    PT pt1,pt2;

    public void dataPT(){
        pt1 = new PT("Ponco Supranoto", "Saya seorang profesional personal trainer yang  dapat membantu anda dalam training.",
                "54", "0812343217", "Gading Serpong", "500 review","5 km", "Power Lifting", 4, 50000,R.drawable.hotshape );

        pt2 = new PT("Theodorus Supranoto", "Saya seorang profesional personal trainer yang  dapat membantu anda dalam training.",
                "54", "0812343217", "Gading Serpong", "500 review","5 km", "Power Lifting", 4, 50000,R.drawable.hotshape );


        listpt.add(pt1);
        listpt.add(pt2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptactivity);
        dataPT();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ptadap = new ListPTAdapter(this, listpt);
        recyclerView.setAdapter(ptadap);
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
                        Intent MoveToHome = new Intent(PTActivity.this, MainActivity.class);
                        startActivity(MoveToHome);
                        break;

                    case R.id.profile:
                        Intent MoveToProfile = new Intent(PTActivity.this, ProfilActivity.class);
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
        final Dialog search = new Dialog(PTActivity.this);

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
                Intent MoveToGymList = new Intent(PTActivity.this, GymActivity.class );
                startActivity(MoveToGymList);
            }
        });

        constraintPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MoveToPTList = new Intent(PTActivity.this, PTActivity.class);
                startActivity(MoveToPTList);
            }
        });

        search.show();
    }
}
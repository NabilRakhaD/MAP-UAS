package com.findurgimmy.umn.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PTActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListPTAdapter ptadap;
    public ArrayList<PT> listpt = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptactivity);
        dataPT();

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

    private void dataPT(){
        db.collection("PersonalTrainer")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()){
                                listpt.add(new PT(document.getString("Nama"), document.getString("Gym"),
                                           Integer.parseInt(document.get("Umur").toString()), document.getString("PhoneNumber"),
                                           Integer.parseInt(document.get("Rating").toString()),Integer.parseInt(document.get("Price").toString()),
                                           document.getString("Picture"), (ArrayList<String>) document.get("Keahlian"),
                                           (ArrayList<String>) document.get("Jadwal")));
                                //Log.d("array", String.valueOf(document.getData()));
                            }

                            recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                            ptadap = new ListPTAdapter(PTActivity.this, listpt);
                            recyclerView.setAdapter(ptadap);
                            recyclerView.setLayoutManager(new LinearLayoutManager(PTActivity.this));
                        } else {
                            Log.w("TAG2", "Error getting documents.", task.getException());
                        }
                    }
                });
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
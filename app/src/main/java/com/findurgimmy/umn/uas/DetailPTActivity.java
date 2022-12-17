package com.findurgimmy.umn.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DetailPTActivity extends AppCompatActivity {
    ImageView ptPic;
    TextView namapt, keahlian, gymterkait, hargapt, btnSesi;
    CheckBox sesi1, sesi2;
    StringBuilder sesipilihan = new StringBuilder();
    String ptname;
    FirebaseUser firebaseUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ptactivity);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        PT pt = (PT) intent.getSerializableExtra("pt");

        ptPic = findViewById(R.id.PTpic);
        namapt = findViewById(R.id.ptname);
        keahlian = findViewById(R.id.listkeahlian);
        gymterkait = findViewById(R.id.gymterkait);
        hargapt = findViewById(R.id.hargaPT);
        sesi1 = findViewById(R.id.sesi1);
        sesi2 = findViewById(R.id.sesi2);
        btnSesi = findViewById(R.id.btnSesi);

        namapt.setText(pt.getNama());
        keahlian.setText(pt.getKeahlian().get(0) + "\n" + pt.getKeahlian().get(1));
        gymterkait.setText("Bertempat di " + pt.getGym());
        hargapt.setText("Harga per sesi : Rp " + pt.getPrice());
        sesi1.setText(pt.getJadwal().get(0));
        sesi2.setText(pt.getJadwal().get(1));

        ptname = String.valueOf(pt.getNama());

        btnSesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pilihansesi();
            }
        });

        BottomNavigationView btmNavView = findViewById(R.id.btmNavigationView);

        btmNavView.setSelectedItemId(R.id.search);

        btmNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        searchGymPT();
                        break;

                    case R.id.home:
                        Intent MoveToHome = new Intent(DetailPTActivity.this, MainActivity.class);
                        startActivity(MoveToHome);
                        break;

                    case R.id.profile:
                        Intent MoveToProfile = new Intent(DetailPTActivity.this, ProfilActivity.class);
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

    private void pilihansesi(){
        if(sesi1.isChecked()){
            sesipilihan.append("\nsesi 1");
        }
        if(sesi2.isChecked()){
            sesipilihan.append("\nsesi 2");
        }
        Map<String, Object> user = new HashMap<>();
        user.put("Personal Trainer", sesipilihan.toString() + "\nOleh " + ptname);
        db.collection("User").document(firebaseUser.getUid())
                .update(user)
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
        Toast.makeText(DetailPTActivity.this, "Anda telah memilih : " + sesipilihan.toString(), Toast.LENGTH_SHORT).show();
        sesipilihan.replace(0, sesipilihan.length(), "");
    }

    protected void searchGymPT(){
        final Dialog search = new Dialog(DetailPTActivity.this);

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
                Intent MoveToGymList = new Intent(DetailPTActivity.this, GymActivity.class );
                startActivity(MoveToGymList);
            }
        });

        constraintPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MoveToPTList = new Intent(DetailPTActivity.this, PTActivity.class);
                startActivity(MoveToPTList);
            }
        });

        search.show();
    }


}
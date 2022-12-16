package com.findurgimmy.umn.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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


}
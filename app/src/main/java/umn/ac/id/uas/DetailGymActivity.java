package umn.ac.id.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class DetailGymActivity extends AppCompatActivity {
     double dLatitude, dLongitude, a, c, dDistance, meterConversion = 1609, myDistance, earthRadius = 3958.75;
     double latitude, longitude;
     TextView nama, tipe, jarak, review, membership, payment, deskripsi, address, remajaPrice, dewasaPrice;
     RadioGroup radioGroup;
     RadioButton radioButton;
     FirebaseFirestore db = FirebaseFirestore.getInstance();
     FirebaseUser firebaseUser;
     String namagym;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gym);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        latitude = Double.parseDouble(sharedPreferences.getString("latitude",""));
        longitude = Double.parseDouble(sharedPreferences.getString("longitude",""));

        Intent intent = getIntent();
        Gym gym = (Gym) intent.getSerializableExtra("gym");

        Log.d("longlat", String.valueOf(latitude));
        Log.d("longlat", String.valueOf(longitude));
        Log.d("longlat", String.valueOf(gym.getLatitude()));
        Log.d("longlat", String.valueOf(gym.getLongitude()));

        nama = findViewById(R.id.namagymDetailGym);
        tipe = findViewById(R.id.tipegymDetailGym);
        jarak = findViewById(R.id.jarakDetailGym);
        review = findViewById(R.id.review);
        deskripsi = findViewById(R.id.desGymDetail);
        address = findViewById(R.id.AddressGymDetail);
        remajaPrice = findViewById(R.id.hargaRemaja);
        dewasaPrice = findViewById(R.id.hargaDewasa);
        membership = findViewById(R.id.membership);

        nama.setText(gym.getNama());
        tipe.setText(gym.getTipe());
        jarak.setText(String.valueOf(getJarak(gym.getLatitude(), gym.getLongitude(), latitude, longitude)) + " km away");
        review.setText(String.valueOf(gym.getReview()) + " Reviews");
        deskripsi.setText(gym.getDeskripsi());
        address.setText(gym.getLokasi());
        remajaPrice.setText(String.valueOf(gym.getTeenagePrice()));
        dewasaPrice.setText(String.valueOf(gym.getAdultPrice()));

        namagym = String.valueOf(gym.getNama());


        membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MembershipModal();
            }
        });

        BottomNavigationView btmNavView = findViewById(R.id.btmNavigationView);

        btmNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        searchGymPT();
                        break;

                    case R.id.home:
                        Intent MoveToHome = new Intent(DetailGymActivity.this, MainActivity.class);
                        startActivity(MoveToHome);
                        break;

                    case R.id.profile:
                        Intent MoveToProfile = new Intent(DetailGymActivity.this, ProfilActivity.class);
                        startActivity(MoveToProfile);
                        break;
                }
                return true;
            }
        });
    }

    private double getJarak(double desLat, double desLong, double currLat, double currLong) {
        dLatitude = Math.toRadians(desLat -  currLat);
        dLongitude = Math.toRadians(desLong - currLong);
        a = (Math.sin(dLatitude/2.0) * Math.sin(dLatitude/2.0) +
                Math.sin(dLongitude/2.0) * Math.sin(dLongitude/2.0)) *
                Math.cos(Math.toRadians(currLat)) *
                Math.cos(Math.toRadians(desLat));
        c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0-a));
        dDistance = earthRadius * c;
        myDistance = dDistance * meterConversion;
        return Double.parseDouble(new DecimalFormat("#.##").format(myDistance/1000.0));
    }

    private void MembershipModal() {

        final Dialog membership = new Dialog(DetailGymActivity.this);

        membership.requestWindowFeature(Window.FEATURE_NO_TITLE);
        membership.setContentView(R.layout.membershipmodal);
        membership.setCancelable(true);

        radioGroup = membership.findViewById(R.id.radiogrupMember);
        payment = membership.findViewById(R.id.payment);

        Window window = membership.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = membership.findViewById(selectedId);

                Map<String, Object> user = new HashMap<>();
                if(radioButton.getText().toString().equals("W E E K L Y")){
                    user.put("Membership", "Weekly di " + namagym);
                }else if(radioButton.getText().toString().equals("M O N T H L Y")){
                    user.put("Membership", "Monthly di " + namagym);
                }else if(radioButton.getText().toString().equals("Y E A R L Y")){
                    user.put("Membership", "Yearly di " + namagym);
                }

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
                Toast.makeText(DetailGymActivity.this, "Anda memilih Membership " + radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
                membership.hide();
            }
        });

        membership.show();
    }


    protected void searchGymPT(){
        final Dialog search = new Dialog(DetailGymActivity.this);

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
                Intent MoveToGymList = new Intent(DetailGymActivity.this, GymActivity.class );
                startActivity(MoveToGymList);
            }
        });

        constraintPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MoveToPTList = new Intent(DetailGymActivity.this, PTActivity.class);
                startActivity(MoveToPTList);
            }
        });

        search.show();
    }
}
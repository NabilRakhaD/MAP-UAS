package umn.ac.id.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DetailGymActivity extends AppCompatActivity {

     TextView nama, tipe, jarak, review, membership, payment;
     RadioGroup radioGroup;
     RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gym);

        Intent intent = getIntent();
        Gym gym = (Gym) intent.getSerializableExtra("gym");

        nama = findViewById(R.id.namagymDetailGym);
        tipe = findViewById(R.id.tipegymDetailGym);
        jarak = findViewById(R.id.jarakDetailGym);
        review = findViewById(R.id.review);
        membership = findViewById(R.id.membership);

        nama.setText(gym.getNama());
        tipe.setText(gym.getTipe());
        jarak.setText(gym.getJarak());
        review.setText(gym.getReview());

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

    private void MembershipModal() {

        final Dialog membership = new Dialog(DetailGymActivity.this);

        membership.requestWindowFeature(Window.FEATURE_NO_TITLE);
        membership.setContentView(R.layout.membershipmodal);
        membership.setCancelable(true);

        radioGroup = findViewById(R.id.radiogrupMember);
        payment = findViewById(R.id.payment);

        Window window = membership.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);

//        payment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int selectedId = radioGroup.getCheckedRadioButtonId();
//                radioButton = findViewById(selectedId);
//                Toast.makeText(DetailGymActivity.this, "Anda memilih Membership " + radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

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
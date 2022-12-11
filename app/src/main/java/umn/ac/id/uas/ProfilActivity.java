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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ProfilActivity extends AppCompatActivity {
    TextView emailProfile, phoneProfile, genderProfile, profilename, changename, manageMember;
    FirebaseUser firebaseUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String radioText;

    TextView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        emailProfile = findViewById(R.id.emailProfile);
        phoneProfile = findViewById(R.id.phoneProfile);
        genderProfile = findViewById(R.id.GenderProfile);
        profilename = findViewById(R.id.profilename);
        manageMember = findViewById(R.id.ManageMember);
        changename = findViewById(R.id.changename);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        getUser();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("61956869598-6eieomiljbdgu84ntjqvd13nk4kd4i93.apps.googleusercontent.com")
                .requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


        changename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog custom = new Dialog(ProfilActivity.this);

                custom.requestWindowFeature(Window.FEATURE_NO_TITLE);
                custom.setContentView(R.layout.customizeprofile);
                custom.setCancelable(true);

                Window window = custom.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.BOTTOM;
                window.setAttributes(wlp);

                radioGroup = custom.findViewById(R.id.radiogrup);
                EditText nama = custom.findViewById(R.id.editNama);
                EditText noHp = custom.findViewById(R.id.editHp);
                TextView submit = custom.findViewById(R.id.submitCustom);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int SelectedId = radioGroup.getCheckedRadioButtonId();
                        radioButton = (RadioButton) custom.findViewById(SelectedId);
                        radioText = radioButton.getText().toString();

                        Map<String, Object> user = new HashMap<>();
                        user.put("Nama", nama.getText().toString());
                        user.put("Jenis Kelamin", radioText);
                        user.put("PhoneNumber", noHp.getText().toString());

                        db.collection("User").document(firebaseUser.getUid())
                                .set(user)
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
                        getUser();
                        custom.hide();
                        Toast.makeText(ProfilActivity.this, "Data berhasil diganti", Toast.LENGTH_SHORT).show();
                    }
                });
                custom.show();
            }
        });


        manageMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog manage = new Dialog(ProfilActivity.this);

                manage.requestWindowFeature(Window.FEATURE_NO_TITLE);
                manage.setContentView(R.layout.managemembership);
                manage.setCancelable(true);

                TextView profilMembership = manage.findViewById(R.id.profilMembership);

                db.collection("User")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        DocumentReference docRef = db.collection("User").document(firebaseUser.getUid());
                                        if(firebaseUser.getUid().equals(document.getId())){
                                            profilMembership.setText(document.getString("Membership"));
                                        }
                                    }
                                } else {
                                    Log.w("TAG2", "Error getting documents.", task.getException());
                                }
                            }
                        });

                manage.show();
            }
        });


        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                if(account!=null){
                    gsc.signOut();
                }
                startActivity(new Intent(ProfilActivity.this, signIn_Activity.class));
                finish();
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
                        Intent MoveToHome = new Intent(ProfilActivity.this, MainActivity.class);
                        startActivity(MoveToHome);
                        break;

                    case R.id.profile:
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
        final Dialog search = new Dialog(ProfilActivity.this);

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
                Intent MoveToGymList = new Intent(ProfilActivity.this, GymActivity.class );
                startActivity(MoveToGymList);
            }
        });

        constraintPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MoveToPTList = new Intent(ProfilActivity.this, PTActivity.class);
                startActivity(MoveToPTList);
            }
        });

        search.show();
    }

    private void getUser() {
        db.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG2", document.getId() + " => " + document.getData());
                                Log.d("TAG2", document.getId() + " " + firebaseUser.getUid());
                                DocumentReference docRef = db.collection("User").document(firebaseUser.getUid());
                                if(firebaseUser.getUid().equals(document.getId())){
                                    profilename.setText(document.getString("Nama"));
                                    emailProfile.setText(firebaseUser.getEmail());
                                    genderProfile.setText(document.getString("Jenis Kelamin"));
                                    phoneProfile.setText(document.getString("PhoneNumber"));
                                }
                            }
                        } else {
                            Log.w("TAG2", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
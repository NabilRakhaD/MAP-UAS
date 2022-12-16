package com.findurgimmy.umn.uas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProfilActivity extends AppCompatActivity {
    TextView emailProfile, phoneProfile, genderProfile, profilename, editprofile, viewMember, viewPT, ambilfoto;
    ImageView profilepic;
    FirebaseUser firebaseUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String radioText, nama;
    Uri imageUri;
    TextView logout;
    StorageReference storageReference;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        emailProfile = findViewById(R.id.emailProfile);
        phoneProfile = findViewById(R.id.phoneProfile);
        genderProfile = findViewById(R.id.GenderProfile);
        profilename = findViewById(R.id.profilename);
        viewMember = findViewById(R.id.ViewMember);
        editprofile = findViewById(R.id.changename);
        ambilfoto = findViewById(R.id.ambilfoto);
        profilepic = findViewById(R.id.profilePic);
        viewPT = findViewById(R.id.ViewPT);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        getUser();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("61956869598-6eieomiljbdgu84ntjqvd13nk4kd4i93.apps.googleusercontent.com")
                .requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


        editprofile.setOnClickListener(new View.OnClickListener() {
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

                        if(nama.getText().length()>0 && noHp.toString().length()>0 && SelectedId != -1){
                            radioText = radioButton.getText().toString();
                            Map<String, Object> user = new HashMap<>();
                            user.put("Nama", nama.getText().toString());
                            user.put("Jenis Kelamin", radioText);
                            user.put("PhoneNumber", noHp.getText().toString());

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
                            getUser();
                            custom.hide();
                            Toast.makeText(ProfilActivity.this, "Data berhasil diganti", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ProfilActivity.this, "Isi semua data terlebih dahulu!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                custom.show();
            }
        });

        ambilfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });


        viewMember.setOnClickListener(new View.OnClickListener() {
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

        viewPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog manage = new Dialog(ProfilActivity.this);

                manage.requestWindowFeature(Window.FEATURE_NO_TITLE);
                manage.setContentView(R.layout.managemembership);
                manage.setCancelable(true);

                TextView profilPT = manage.findViewById(R.id.profilMembership);
                TextView title = manage.findViewById(R.id.textView2);
                title.setText("Personal Trainer");

                db.collection("User")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        DocumentReference docRef = db.collection("User").document(firebaseUser.getUid());
                                        if(firebaseUser.getUid().equals(document.getId())){
                                            profilPT.setText(document.getString("Personal Trainer"));
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

    private void selectPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String filename = nama + " ("+firebaseUser.getUid()+")";
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Upload Foto");
        progressDialog.setMessage("Foto sedang diubah");
        if(requestCode == 100 && data != null && data.getData() != null){
            imageUri = data.getData();

            progressDialog.show();
            storageReference = FirebaseStorage.getInstance().getReference("Pictures/"+filename);
            storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("Gambar", uri );
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
                                }
                            });
                            getUser();
                            progressDialog.dismiss();
                            Toast.makeText(ProfilActivity.this, "Foto berhasil diubah", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfilActivity.this, "Foto gagal diubah", Toast.LENGTH_SHORT).show();
                        }
                    });
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
                                    Picasso.get().load(document.getString("Gambar")).into(profilepic);
                                    nama = document.getString("Nama");
                                }
                            }
                        } else {
                            Log.w("TAG2", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
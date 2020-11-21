package com.example.cms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profilePic;
    private TextView profileName, profileId, profileDepartment, profileMobile, profileEmail, profileGender, profileType;
    private Button profileUpdate, changePassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");

        profilePic = findViewById(R.id.profile_pic);
        profileName = findViewById(R.id.profile_name);
        profileId = findViewById(R.id.profile_employeeid);
        profileDepartment = findViewById(R.id.profile_department);
        profileMobile = findViewById(R.id.profile_mobile);
        profileEmail = findViewById(R.id.profile_username);
        profileGender = findViewById(R.id.profile_gender);
        profileType =findViewById(R.id.profile_type);
        profileUpdate = findViewById(R.id.profile_edit);
        changePassword = findViewById(R.id.profile_changepassword);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // back navigation for moving to prv. activity

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        //String typ = userProfile.getType();

        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        //final DatabaseReference databaseReference = firebaseDatabase.getReference(typ).child(firebaseUser.getUid());

        /*
        if(typ.equals("User")) {
            DatabaseReference databaseReference1 = databaseReference.child("User").child(firebaseAuth.getUid());//.child(firebaseUser.getUid());
            databaseReference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userProfile = dataSnapshot.getValue(UserProfile.class);
                    profileName.setText("Name: " + userProfile.getUsername());
                    profileId.setText("Employee Id: " + userProfile.getUserid());
                    profileDepartment.setText("Department: " + userProfile.getDepartment());
                    profileMobile.setText("Mobile No: " + userProfile.getMobile());
                    profileEmail.setText("Username: " + userProfile.getUseremail());
                    profileGender.setText("Gender: " + userProfile.getGender());
                    profileType.setText("Type: " + userProfile.getType());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        else if(typ.equals("Supervisor")) {
            DatabaseReference databaseReference2 = databaseReference.child("Supervisor").child(firebaseAuth.getUid());//.child(firebaseUser.getUid());
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userProfile = dataSnapshot.getValue(UserProfile.class);
                    profileName.setText("Name: " + userProfile.getUsername());
                    profileId.setText("Employee Id: " + userProfile.getUserid());
                    profileDepartment.setText("Department: " + userProfile.getDepartment());
                    profileMobile.setText("Mobile No: " + userProfile.getMobile());
                    profileEmail.setText("Username: " + userProfile.getUseremail());
                    profileGender.setText("Gender: " + userProfile.getGender());
                    profileType.setText("Type: " + userProfile.getType());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

                }
            });
        } */

        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(profilePic);

            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userProfile = dataSnapshot.getValue(UserProfile.class);
                profileName.setText("Name: " + userProfile.getUsername());
                profileId.setText("Employee Id: " + userProfile.getUserid());
                profileDepartment.setText("Department: " + userProfile.getDepartment());
                profileMobile.setText("Mobile No: " + userProfile.getMobile());
                profileEmail.setText("Username: " + userProfile.getUseremail());
                profileGender.setText("Gender: " + userProfile.getGender());
                profileType.setText("Type: " + userProfile.getType());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });

/*
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                userProfile = dataSnapshot.getValue(UserProfile.class);
                profileName.setText("Name: " + userProfile.getUsername());
                profileId.setText("Employee Id: " + userProfile.getUserid());
                profileDepartment.setText("Department: " + userProfile.getDepartment());
                profileMobile.setText("Mobile No: " + userProfile.getMobile());
                profileEmail.setText("Username: " + userProfile.getUseremail());
                profileGender.setText("Gender: " + userProfile.getGender());
                profileType.setText("Type: " + userProfile.getType());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }); */

        profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, UpdateProfileActivity.class));
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, UpdatePasswordActivity.class));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

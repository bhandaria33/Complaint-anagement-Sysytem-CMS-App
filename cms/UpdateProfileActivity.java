package com.example.cms;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cms.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class UpdateProfileActivity extends AppCompatActivity {

    private ImageView updateProfile;
    private EditText updateName, updateId, updateDepartment, updateMobile, updateEmail; //updateGender, updateType
    private Button save;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE=123;
    Uri imagepath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() !=null){
            imagepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagepath);
                updateProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        getSupportActionBar().setTitle("Update Profile");

        updateProfile = findViewById(R.id.profile_pic);
        updateName = findViewById(R.id.update_name);
        updateId = findViewById(R.id.update_employeeid);
        updateDepartment= findViewById(R.id.update_department);
        updateMobile = findViewById(R.id.update_mobile);
        updateEmail = findViewById(R.id.update_email);
        //updateGender = findViewById(R.id.update_gender);
        //updateType = findViewById(R.id.update_type);
        save = findViewById(R.id.update_save);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                updateName.setText(userProfile.getUsername());
                updateId.setText(userProfile.getUserid());
                updateDepartment.setText(userProfile.getDepartment());
                updateMobile.setText(userProfile.getMobile());
                updateEmail.setText(userProfile.getUseremail());
                //updateGender.setText(userProfile.getGender());
                //updateType.setText(userProfile.getType());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });

        final StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(updateProfile);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = updateName.getText().toString();
                String id = updateId.getText().toString();
                String dept = updateDepartment.getText().toString();
                String mob = updateMobile.getText().toString();
                String email = updateEmail.getText().toString();
                //String gender = updateGender.getText().toString();
                //String type = updateType.getText().toString();

                UserProfile userProfile = new UserProfile(name, id, dept, mob, email);

                databaseReference.setValue(userProfile);

                StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic"); // User Id/Images/Profile Pic.png
                UploadTask uploadTask = imageReference.putFile(imagepath);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfileActivity.this,"Upload Failed",Toast.LENGTH_SHORT).show();

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(UpdateProfileActivity.this,"Upload Successful",Toast.LENGTH_SHORT).show();

                    }
                });

                finish();
            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*"); // application/*  audio/*
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}

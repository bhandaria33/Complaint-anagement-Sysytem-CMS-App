package com.example.cms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ComplaintActivity extends AppCompatActivity {

    //EditText ref_id;
    EditText subject, specify;
    // private ImageView profile;
    Button submit;
    private String subj, specf; //refid;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    private String ref;

    /*
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private static int PICK_IMAGE=123;
    Uri imagepath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() !=null){
            imagepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagepath);
                profile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        //ref_id = findViewById(R.id.comp_refid);
        subject = findViewById(R.id.subject);
        specify = findViewById(R.id.specify);
        submit = findViewById(R.id.submit);
        // profile = findViewById(R.id.uploadpic);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        /*
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*"); // application/*  audio/*
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);

            }
        });
        */

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                subj = subject.getText().toString().trim();
                specf = specify.getText().toString().trim();
                //refid = ref_id.getText().toString().trim();

                if(TextUtils.isEmpty(subj) || TextUtils.isEmpty(specf) ) {
                    Toast.makeText(ComplaintActivity.this, "Please enter all details", Toast.LENGTH_LONG).show();
                    return;
                }

                else {
                    progressDialog.setMessage("Processing");
                    progressDialog.show();

                    final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        progressDialog.dismiss();

                        sendUserData();

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssms");
                        LocalDateTime now = LocalDateTime.now();
                        ref = dtf.format(now);

                        Toast.makeText(ComplaintActivity.this, "Complaint Lodged Successfully, Reference Id: " + ref, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(ComplaintActivity.this, "Complaint Refernece Id: " + ref, Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(ComplaintActivity.this, ComplaintRegistrationActivity.class));
                    }
                    else{
                        Toast.makeText(ComplaintActivity.this,"Complaint Lodging Fialed",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        if (firebaseAuth.getUid() == null) {
            throw new NullPointerException("Can't pass null for argument 'pathString' in FirebaseDatabase.getReference()");
        }
        else{
            //DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
            DatabaseReference compRef = firebaseDatabase.getReference(firebaseAuth.getUid()).child("Complaints/Account");
            /*
            StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Complaint Photos"); // User_Id/Images/Profile Pic.png
            //StorageReference newImageReference = imageReference.push();
            UploadTask uploadTask = imageReference.putFile(imagepath);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ComplaintActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ComplaintActivity.this, "Data Upload Successful", Toast.LENGTH_SHORT).show();

                }
            });
            */

            DatabaseReference newCompRef = compRef.push();
            newCompRef.setValue(new UserProfile(subj, specf));

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

package com.example.cms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.sql.Ref;

public class SignupActivity extends AppCompatActivity {

    private ImageView profile;
    private EditText name, id, department,mobile, username, password, rpassword;
    private Button user_register;
    private RadioGroup rgender, rtype;
    private RadioButton gender, type;
    String Name, empid, dept, mob, email, pswd, rpswd, gndr, typ;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private static int PICK_IMAGE=123;
    Uri imagepath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        profile = findViewById(R.id.profile);
        name = findViewById(R.id.name);
        id = findViewById(R.id.employee_id);
        department = findViewById(R.id.department);
        mobile = findViewById(R.id.mobile);
        username = findViewById(R.id.register_username);
        password = findViewById(R.id.register_password);
        rpassword = findViewById(R.id.register_repassword);
        rgender = findViewById(R.id.gender);
        rtype = findViewById(R.id.type);
        user_register = findViewById(R.id.register);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
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

        rgender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                gender = rgender.findViewById(i);
                switch (i){
                    case R.id.male:
                        gndr = gender.getText().toString().trim();
                        break;
                    case R.id.female:
                        gndr = gender.getText().toString().trim();
                        break;
                }
            }
        });

        rtype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int j) {
                type = rtype.findViewById(j);
                switch (j){
                    case R.id.user_user:
                        typ = type.getText().toString().trim();
                        break;
                    case R.id.user_supervisor:
                        typ = type.getText().toString().trim();
                        break;
                }
            }
        });

        user_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Name = name.getText().toString().trim();
                empid = id.getText().toString().trim();
                dept = department.getText().toString().trim();
                mob = mobile.getText().toString().trim();
                email = username.getText().toString().trim();
                pswd = password.getText().toString().trim();
                rpswd = rpassword.getText().toString().trim();

                if(TextUtils.isEmpty(Name) || TextUtils.isEmpty(empid) || TextUtils.isEmpty(dept) || TextUtils.isEmpty(mob) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pswd) || TextUtils.isEmpty(rpswd) || imagepath == null) {
                    Toast.makeText(SignupActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                    return;
                }

                if( pswd.length() < 6 || pswd.length() > 15 )
                {
                    Toast.makeText(SignupActivity.this, "Password should be between 6-15", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mob.length() != 10) {
                    Toast.makeText(SignupActivity.this, "Invalid mobile no", Toast.LENGTH_SHORT).show();
                }

                if( pswd.equals(rpswd)){
                    progressDialog.setMessage("Processing");
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(email, pswd)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();

                                        //sendEmailVerification();
                                        sendUserData();
                                        // if firebaseAuth.signOut() is removed then the statement if(user!null) will make the app to go to the ComplaintRegistrationActivity otherwise will go to the LoginActivity
                                        firebaseAuth.signOut();
                                        Toast.makeText(SignupActivity.this,"Successfully Registered, Upload complete",Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));

                                        // Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                        // startActivity(new Intent(SignupActivity.this, LoginActivity.class));


                                    } else {
                                        Toast.makeText(SignupActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();

                                    }

                                    // ...
                                }
                            });
                }
                else

                    Toast.makeText(SignupActivity.this, "Password doesn't match",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void sendEmailVerification(){
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(SignupActivity.this,"Successfully Registered, Verification mail sent",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    }
                    else{
                        Toast.makeText(SignupActivity.this,"Verification mail hasn't been sent",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /*
    public void onRadiobuttonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.male:
                if(checked)
                    break;

            case R.id.female:
                if(checked)
                    break;
        }
    }
    */

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        if (firebaseAuth.getUid() == null) {
            throw new NullPointerException("Can't pass null for argument 'pathString' in FirebaseDatabase.getReference()");
        }
        else{


            if(typ.equals("User")){
                DatabaseReference myRef1 = firebaseDatabase.getReference(typ).child(firebaseAuth.getUid());
                DatabaseReference newRef1 = myRef1.push();
                newRef1.setValue(new UserProfile(Name, empid, dept, mob ,email,gndr, typ));
            }
            else if(typ.equals("Supervisor")){
                DatabaseReference myRef2 = firebaseDatabase.getReference(typ).child(firebaseAuth.getUid());
                DatabaseReference newRef2 = myRef2.push();
                newRef2.setValue(new UserProfile(Name, empid, dept, mob ,email,gndr, typ));
            }

            //DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
            StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic"); // User_Id/Images/Profile Pic.png
            UploadTask uploadTask = imageReference.putFile(imagepath);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignupActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(SignupActivity.this, "Data Upload Successful", Toast.LENGTH_SHORT).show();

                }
            });
            //UserProfile userProfile = new UserProfile(Name, empid, dept, mob ,email,gndr, typ);
            //myRef.setValue(userProfile);
        }
    }

}

package com.example.cms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class FinanceCompActivity extends AppCompatActivity {

    //private EditText ref_id;
    private EditText subject, specify;
    private Button submit;
    private String subj, specf; //refid;
    private String ref;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_comp);

        //ref_id = findViewById(R.id.fincomp_refid);
        subject = findViewById(R.id.subject);
        specify = findViewById(R.id.specify);
        submit = findViewById(R.id.submit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                subj = subject.getText().toString().trim();
                specf = specify.getText().toString().trim();
                //refid = ref_id.getText().toString().trim();

                if(TextUtils.isEmpty(subj) || TextUtils.isEmpty(specf) ) {
                    Toast.makeText(FinanceCompActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(FinanceCompActivity.this, "Complaint Lodged Successfully, Reference Id: " + ref, Toast.LENGTH_LONG).show();
                        //Toast.makeText(FinanceCompActivity.this, "Complaint Refernece Id: " + refid, Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(FinanceCompActivity.this, ComplaintRegistrationActivity.class));
                    }
                    else{
                        Toast.makeText(FinanceCompActivity.this,"Complaint Lodging Fialed",Toast.LENGTH_SHORT).show();
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
            DatabaseReference compRef = firebaseDatabase.getReference(firebaseAuth.getUid()).child("Complaints/Finance");

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

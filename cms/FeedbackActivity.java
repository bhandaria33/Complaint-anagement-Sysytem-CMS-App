package com.example.cms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackActivity extends AppCompatActivity {

    EditText subject, specify;
    Button submit;
    String subj, specf;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getSupportActionBar().setTitle("Feedback");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        subject = findViewById(R.id.subject);
        specify = findViewById(R.id.specify);
        submit = findViewById(R.id.submit);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subj = subject.getText().toString().trim();
                specf = specify.getText().toString().trim();

                if(TextUtils.isEmpty(subj) || TextUtils.isEmpty(specf) ) {
                    Toast.makeText(FeedbackActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    progressDialog.setMessage("Processing");
                    progressDialog.show();

                    final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        progressDialog.dismiss();

                        sendUserData();

                        Toast.makeText(FeedbackActivity.this, "Thank you for the Feedback", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(FeedbackActivity.this, HomeActivity.class));
                    }
                    else{
                        Toast.makeText(FeedbackActivity.this,"Feedback Submission Failed",Toast.LENGTH_SHORT).show();
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
            DatabaseReference Ref = firebaseDatabase.getReference(firebaseAuth.getUid()).child("Feedback");
            DatabaseReference newRef = Ref.push();
            newRef.setValue(new UserProfile(subj, specf));

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

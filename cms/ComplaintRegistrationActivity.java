package com.example.cms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ComplaintRegistrationActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button accounts, finance;
    //private Button it, civil, electrical, erp, maintenance, other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_registration);
        getSupportActionBar().setTitle("Register Complaint");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        accounts = findViewById(R.id.accounts);
        finance = findViewById(R.id.finance);

        /*
        it = findViewById(R.id.it);
        civil = findViewById(R.id.civil);
        electrical = findViewById(R.id.electrical);
        erp =findViewById(R.id.erp);
        maintenance = findViewById(R.id.maintenance);
        other = findViewById(R.id.others);
        */

        accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComplaintRegistrationActivity.this, ComplaintActivity.class));
            }
        });

        finance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComplaintRegistrationActivity.this, FinanceCompActivity.class));
            }
        });

        /*
        sales,marketimg,R&D,production,precurement
        it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComplaintRegistrationActivity.this, ComplaintActivity.class));
            }
        });

        civil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComplaintRegistrationActivity.this, ComplaintActivity.class));
            }
        });

        electrical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComplaintRegistrationActivity.this, ComplaintActivity.class));
            }
        });

        erp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComplaintRegistrationActivity.this, ComplaintActivity.class));
            }
        });

        maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComplaintRegistrationActivity.this, ComplaintActivity.class));
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComplaintRegistrationActivity.this, ComplaintActivity.class));
            }
        });

    }

    private void Logout() {
        firebaseAuth.signOut();
        Toast.makeText(ComplaintRegistrationActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
        finish();
    }
    */

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

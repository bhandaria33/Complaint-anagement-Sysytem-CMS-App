package com.example.cms;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyComplaintsActivity extends AppCompatActivity {

    TextView subject, specify;
    // private ImageView profile;
    //String dept, mob, subj, specf;

    private ListView listView;
    //ArrayList<UserProfile> compList = new ArrayList<>();
    //private Adapter_Complaints cAdapter;
    ArrayList<UserProfile> compList = new ArrayList<>();
    private Adapter_Complaints cAdapter;
    //private Adapter_Complaints <String> cAdapter;
    UserProfile userProfile;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_complaints);
        getSupportActionBar().setTitle("Complaints");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        subject = findViewById(R.id.comp_subject);
        specify = findViewById(R.id.comp_specify);

        listView = findViewById(R.id.complaint_list);

        cAdapter = new Adapter_Complaints(this,compList);
        listView.setAdapter(cAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        /*
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    userProfile = ds.getValue(UserProfile.class);
                    compList.add(userProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyComplaintsActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });
        */


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                compList.add(dataSnapshot.child("Complaints").getValue(UserProfile.class));
                cAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                compList.remove(dataSnapshot.getValue(String.class));
                cAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        /*
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                department.setText("Department: " + userProfile.getDepartment());
                mobile.setText("Mobile: " + userProfile.getMobile());
                subject.setText("Subject: " + userProfile.getSubject());
                specify.setText("Specify: " + userProfile.getSpecify());
                System.out.println("Previous Post ID: " + prevChildKey);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MyComplaintsActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
        */
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

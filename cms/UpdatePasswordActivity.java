package com.example.cms;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordActivity extends AppCompatActivity {

    private EditText newPassword, cnfrmPassword;
    private Button change;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        getSupportActionBar().setTitle("Update Password");

        newPassword = findViewById(R.id.pswd_newPassword);
        cnfrmPassword = findViewById(R.id.pswd_confirmPassword);
        change = findViewById(R.id.pswd_changePassword);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newPswd = newPassword.getText().toString();
                String cnfrmPswd = cnfrmPassword.getText().toString();

                if(newPswd.equals(cnfrmPswd)) {
                    firebaseUser.updatePassword(newPswd).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UpdatePasswordActivity.this, "Password successfully updated", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(UpdatePasswordActivity.this, "Password update failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else{
                    Toast.makeText(UpdatePasswordActivity.this, "Passwords didn't match ", Toast.LENGTH_SHORT).show();
                }
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

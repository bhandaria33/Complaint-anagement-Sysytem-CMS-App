package com.example.cms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class AboutUsActivity extends AppCompatActivity {

    private TextView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getSupportActionBar().setTitle("About Us");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        about = findViewById(R.id.about_us);

        String text="";
        try{
            InputStream inputStream = getAssets().open("about_us.txt");
            int size = inputStream.available();
            byte [] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            text = new String(buffer);

        }catch (IOException e){
            e.printStackTrace();
        }
        about.setText(text);

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

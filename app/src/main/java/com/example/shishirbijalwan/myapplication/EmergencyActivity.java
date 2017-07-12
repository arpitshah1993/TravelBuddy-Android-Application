package com.example.shishirbijalwan.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class EmergencyActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_activity);





    }


    public void policecall(View view) {
        Intent callIntent=new Intent(Intent.ACTION_CALL);
        //get data from user
        callIntent.setData(Uri.parse("tel:9795876340"));
        startActivity(callIntent);
    }

    public void embassycall(View view) {
        Intent callIntent=new Intent(Intent.ACTION_CALL);
        //get data from user
        callIntent.setData(Uri.parse("tel:9795876340"));
        startActivity(callIntent);
    }

    public void medicalcall(View view) {
        Intent callIntent=new Intent(Intent.ACTION_CALL);
        //get data from user
        callIntent.setData(Uri.parse("tel:9795876340"));
        startActivity(callIntent);
    }
}

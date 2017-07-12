package com.example.shishirbijalwan.myapplication;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.amazonaws.mobileconnectors.lambdainvoker.*;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    StringBuilder Result;
    TextView resultTextview;
    Button loginButton;
    TextView createAccountButton;
    TextView userEmail;
    TextView userpassword;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User.setInstance();

        user = User.getInstance();

        //Variables of this activity
        loginButton = (Button) findViewById(R.id.login);
        createAccountButton = (TextView) findViewById(R.id.newUser);

        //on click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                 userEmail = (TextView) findViewById(R.id.emailIdText);
                 userpassword = (TextView) findViewById(R.id.passwordTextBox);

                if(userEmail.length()==0){
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(findViewById(R.id.emailIdText));
                    return;

                }

                if(userpassword.length()==0){
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(findViewById(R.id.passwordTextBox));
                    return;
                }

                userEmail.setOnClickListener(this);

                userpassword.setOnClickListener(this);
                user.tempPassword = userpassword.getText().toString();
                user.tempUser = userEmail.getText().toString();
                new getData().execute("ec2-34-205-50-71.compute-1.amazonaws.com:9000");


            }
        });
        //on click listerner for create new account
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                resultTextview.setText("Going to create a account");
                // startActivity( new Intent(MainActivity.this, CreateAccount.class));
                Intent i = new Intent(MainActivity.this, CreateAccount.class);
                Bundle bundle = new Bundle();
                Gson gS = new Gson();
                String target = gS.toJson(user); // Converts the object to a JSON String
                i.putExtra("MyObjectAsString", target);
                startActivity(i);
                // bundle.putBundle("user",user);

            }
        });
        resultTextview = (TextView) findViewById(R.id.wrongCredentialTextBox);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }



    class getData extends AsyncTask<String, Void, Boolean> {


        @Override
        protected Boolean doInBackground(String... params) {


            Boolean status = user.validateUser();


            return status;
        }

        // Get a string from an input stream

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (!result)
                resultTextview.setText("Invalid credential");
            YoYo.with(Techniques.Flash)
                    .duration(700)
                    .repeat(2)
                    .playOn(findViewById(R.id.wrongCredentialTextBox));
            if (result) {
                resultTextview.setText("You got it write");
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));

            }

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(0);
        finish();
        super.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }
}

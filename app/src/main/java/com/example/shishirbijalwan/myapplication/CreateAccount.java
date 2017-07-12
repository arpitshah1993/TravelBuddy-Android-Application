package com.example.shishirbijalwan.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class CreateAccount extends AppCompatActivity {

    TextView nameTextView;
    TextView emailTextView;
    TextView passwordTextViewOne;
    TextView passwordTextViewTwo;
    TextView dobTextView;
    Button createNewAccountButton;
    TextView userAlreadyPresentTextView;

    User user;

    public boolean isNullOrEmpty(String param) {
        // doesn't ignore spaces, but does save an object creation.
        return param.length()==0;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // xml entities
        nameTextView = (TextView) findViewById(R.id.userName);
        emailTextView = (TextView) findViewById(R.id.newUserEmail);
        passwordTextViewOne = (TextView) findViewById(R.id.passwordTextBoxOne);
        passwordTextViewTwo = (TextView) findViewById(R.id.passwordTextBoxTwo);
        dobTextView = (TextView) findViewById(R.id.dobUser);
        createNewAccountButton = (Button) findViewById(R.id.createNewUserButton);
        userAlreadyPresentTextView = (TextView) findViewById(R.id.alreadyAccountTextView);
        //Json class Object
        String target = getIntent().getStringExtra("MyObjectAsString");
        Gson gS = new Gson();
        user = gS.fromJson(target, User.class); // Converts the JSON String to an Object


        //onclick operations
        createNewAccountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                user.tempPassword = passwordTextViewOne.getText().toString();
                user.tempUser = emailTextView.getText().toString();
                user.tempName = nameTextView.getText().toString();
                user.tempDate = dobTextView.getText().toString();
                if(isNullOrEmpty(user.tempPassword) ||isNullOrEmpty(user.tempUser) ||isNullOrEmpty(user.tempName) ||isNullOrEmpty(user.tempDate))
                {
                    userAlreadyPresentTextView.setText("Fill the empty field.");
                }
                else if (!user.tempPassword.equals(passwordTextViewTwo.getText().toString())) {

                    userAlreadyPresentTextView.setText("Please enter same password");
                }
                else {
                    if(!checkConditions(user.tempUser)) {

                        userAlreadyPresentTextView.setText("Please enter a valid email id");
                    }else{
                        new newAccountAsync().execute("hi");


                    }

                }

            }
        });


    }

public Boolean checkConditions(String str){


    if(str.indexOf('@')<0 || str.indexOf('.') <0){
        return false;
    }



   return true;
}
    class newAccountAsync extends AsyncTask<String, Void, Boolean> {


        @Override
        protected Boolean doInBackground(String... params) {


            Boolean bol = user.createUser();

            return bol;
        }

        // Get a string from an input stream

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (!result) {
                userAlreadyPresentTextView.setText("Either user is already present or insufficiect");

            } else {

                startActivity(new Intent(CreateAccount.this, WelcomeActivity.class));


            }

        }
    }


}

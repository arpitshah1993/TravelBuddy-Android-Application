package com.example.shishirbijalwan.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class newDiaryEntryActivity extends AppCompatActivity {

    TextToSpeech tt;
    private TextView text;
    private String InputString;
    private String OutputString;
    private Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    private String Title;
    String currentLanguage="en";
    private static final String[]paths = {"item 1", "item 2", "item 3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        text=(TextView)findViewById(R.id.textViewdata);
        tt = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR)
                    tt.setLanguage(new Locale(currentLanguage));
            }
        });

        ImageButton speakButton=(ImageButton)findViewById(R.id.speakButtonn);
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(v);
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_diary_entry);
    }

    public void onButtonClick(View v){

        text=(TextView)findViewById(R.id.textViewdata);
        promtSpeechInput();
    }


    public void onDiarySaveClick(View v){

        OutputString=((TextView)findViewById(R.id.textViewdata)).getText().toString();

        Title=((TextView)findViewById(R.id.titleText)).getText().toString();
        new MyDownLoadJsonAsyncTask().execute("");
        finish();
    }



    public void promtSpeechInput(){

        Intent i= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say something");

        try {

            startActivityForResult(i,100);
        }catch (ActivityNotFoundException a){

            Toast.makeText(newDiaryEntryActivity.this,"Sorry your device doen't support voice recognition",Toast.LENGTH_LONG).show();
        }

    }


    public void onActivityResult(int request_code,int result_code,Intent i){

        super.onActivityResult(request_code,result_code,i);

        switch (request_code){

            case 100: if(result_code== RESULT_OK & i!=null){
                ArrayList<String> result=i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String str= text.getText().toString();

                if(str.length()==0)
                    text.setText(result.get(0));
                else
                    text.setText(str+". "+result.get(0));
                InputString=result.get(0);
                //new MyDownLoadJsonAsyncTask().execute("");
            }
                break;
        }

    }

    public void speak(View v){

        Toast.makeText(getApplicationContext(),"speak called",Toast.LENGTH_SHORT).show();
        String str= text.getText().toString();

        tt.speak(str, TextToSpeech.QUEUE_FLUSH,null,null);
    }

    private class MyDownLoadJsonAsyncTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String[] params) {

            HashMap sendObject = new HashMap();
            //user object email
            User user= User.getInstance();
            sendObject.put("email", user.email);
            sendObject.put("data", OutputString);
            sendObject.put("title",Title);
            JSONObject js = new JSONObject(sendObject);
            String url="http://ec2-34-205-50-71.compute-1.amazonaws.com:9000/";
            Utility utility= new Utility();
            utility.sendHttPostRequestTwo(url + "newDiaryEntry", js);
            return  "Hey";
        }


        @Override
        protected void onPostExecute(String message) {

        }

    }
}

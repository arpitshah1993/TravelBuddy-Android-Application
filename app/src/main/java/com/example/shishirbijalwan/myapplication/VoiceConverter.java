package com.example.shishirbijalwan.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

public class VoiceConverter extends AppCompatActivity {
    TextToSpeech tt;
    private TextView text;
    private String InputString;
    private String OutputString;
    private Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    String currentLanguage="de";
    private static final String[]paths = {"item 1", "item 2", "item 3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        tt = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR)
                    tt.setLanguage(new Locale(currentLanguage));
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_converter);

        spinner = (Spinner) findViewById(R.id.planets_spinner);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        /*adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, paths);
*/
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(0xFFFFFFFF);
                String str=String.valueOf(position);
                //Toast.makeText(MainActivity.this,"to string"+str,Toast.LENGTH_LONG).show();

                if(position==0){

                    currentLanguage="hi";
                    tt = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR)
                                tt.setLanguage(new Locale(currentLanguage));
                        }
                    });
                }else if(position==1 ){
                    currentLanguage="ko";
                    tt = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR)
                                tt.setLanguage(new Locale(currentLanguage));
                        }
                    });

                }else if(position==2 ){
                    currentLanguage="fr";
                    tt = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR)
                                tt.setLanguage(new Locale(currentLanguage));
                        }
                    });

                }else if(position==3 ){
                    currentLanguage="ru";
                    tt = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR)
                                tt.setLanguage(new Locale(currentLanguage));
                        }
                    });

                }else if(position==4){

                    currentLanguage="de";
                    tt = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR)
                                tt.setLanguage(new Locale(currentLanguage));
                        }
                    });

                }else if(position==5){

                    currentLanguage="zh-CN";
                    tt = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR)
                                tt.setLanguage(new Locale(currentLanguage));
                        }
                    });

                }else if(position==6){

                    currentLanguage="ja";
                    tt = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR)
                                tt.setLanguage(new Locale(currentLanguage));
                        }
                    });

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //linear
        LinearLayout lr= (LinearLayout) findViewById(R.id.voiceconvertorlayout);
Bitmap bmImg=decodeSampledBitmapFromResource(getResources(), R.drawable.blackbg, 200, 200);
        BitmapDrawable background = new BitmapDrawable(bmImg);
        lr.setBackgroundDrawable(background);


    }

    public void onButtonClick(View v){

        text=(TextView)findViewById(R.id.textView2);
        promtSpeechInput();
    }

    public void promtSpeechInput(){

        Intent i= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say something");

        try {

            startActivityForResult(i,100);
        }catch (ActivityNotFoundException a){

            Toast.makeText(VoiceConverter.this,"Sorry your device doen't support voice recognition",Toast.LENGTH_LONG).show();
        }

    }


    public void onActivityResult(int request_code,int result_code,Intent i){

        super.onActivityResult(request_code,result_code,i);

        switch (request_code){

            case 100: if(result_code== RESULT_OK & i!=null){
                ArrayList<String> result=i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                text.setText(result.get(0));
                InputString=result.get(0);
                new MyDownLoadJsonAsyncTask().execute("");
            }
                break;
        }

    }

    public void speak(View v){

        Toast.makeText(getApplicationContext(),"speak called",Toast.LENGTH_SHORT).show();
        tt.speak(OutputString, TextToSpeech.QUEUE_FLUSH,null,null);
    }

    private class MyDownLoadJsonAsyncTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String[] params) {
            // do above Server call here
            try {
                InputString=  URLEncoder.encode(InputString,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            OutputString=Utility.downloadJSONusingHTTPGetRequest("https://www.googleapis.com/language/translate/v2?q="+InputString+"&target="+currentLanguage+"&source=en&key=AIzaSyAM9EVP4FeV1GOyqAprBsy-RkoT-Fgy-x4");
            try {
                JSONObject obj=new JSONObject(OutputString);
                OutputString=((JSONObject)((JSONArray)((JSONObject)obj.get("data")).get("translations")).get(0)).get("translatedText").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(OutputString);
            return OutputString;
        }


        @Override
        protected void onPostExecute(String message) {
            text.setText(message);
            tt.speak(message, TextToSpeech.QUEUE_FLUSH,null,null);


        }

    }



    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


}


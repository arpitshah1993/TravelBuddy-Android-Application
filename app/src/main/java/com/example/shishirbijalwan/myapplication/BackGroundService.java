package com.example.shishirbijalwan.myapplication;

import android.app.Service;
import android.content.Intent;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.IBinder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class BackGroundService extends Service {
    private String InputString;
    private String OutputString;
    private String Longitute;
    private String Latitude;
    double lat=0;
    double lng=0;

    public BackGroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timer timer = new Timer();
        timer.schedule(new SayHello(), 0, 660000);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopping the player when service is destroyed
    }

    //helper class
    class SayHello extends TimerTask {
        public void run() {
            getcurrentLocation();
            new MyDownLoadJsonAsyncTask().execute("hey");

        }


        private void getcurrentLocation(){
            Geocoder geocoder;
            String bestProvider;
            List<Address> user = null;

            GPSTracker tracker = new GPSTracker(BackGroundService.this);
            if (!tracker.canGetLocation()) {
                tracker.showSettingsAlert();
            } else {
                lat = tracker.getLatitude();
                lng = tracker.getLongitude();
            }
            Latitude= String.valueOf(lat);
            Longitute= String.valueOf(lng);

            // Toast.makeText(ServiceClass.this,"Latitue"+str+" "+str2,Toast.LENGTH_SHORT).show();

        }

    }





    private class MyDownLoadJsonAsyncTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String[] params) {
            // do above Server call here

            OutputString=Utility.downloadJSONusingHTTPGetRequest("http://api.openweathermap.org/data/2.5/weather?lat="+Latitude+"&lon="+Longitute+"&appid=44a65448b32f0ee01cbfa3b5ada9f1e1");

            try {
                JSONObject obj=new JSONObject(OutputString);
                OutputString=((JSONObject)obj.get("main")).getString("temp");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(OutputString);
            return OutputString;
        }


        @Override
        protected void onPostExecute(String message) {


            User ur= User.getInstance();
            Double celciusVal= Double.parseDouble(message)-273;
            System.out.println(celciusVal);
            double d=(double)9/(double)5;
            double a=(d)* celciusVal.doubleValue();
            double b=32;
            double c=a+b;
            ur.temperature=String.valueOf(c);
        }

    }

}

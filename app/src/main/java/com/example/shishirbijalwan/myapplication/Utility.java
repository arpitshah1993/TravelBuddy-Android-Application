package com.example.shishirbijalwan.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by shishirbijalwan on 4/13/17.
 */

public class Utility {

//to get data from server get request
    public static String downloadJSONusingHTTPGetRequest(String urlString) {
        String jsonString=null;

        try {
            URL url = new URL(urlString);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = httpConnection.getInputStream();
                jsonString = getStringfromStream(stream);
            }
            httpConnection.disconnect();
        }  catch (UnknownHostException e1) {
            Log.d("MyDebugMsg", "UnknownHostexception in downloadJSONusingHTTPGetRequest");
            e1.printStackTrace();
        } catch (Exception ex) {
            Log.d("MyDebugMsg", "Exception in downloadJSONusingHTTPGetRequest");
            ex.printStackTrace();
        }
        return jsonString;
    }

    public static Bitmap downloadImageusingHTTPGetRequest(String urlString) {
        Bitmap image=null, line;

        try {
            URL url = new URL(urlString);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = httpConnection.getInputStream();
                image = getImagefromStream(stream);
            }
            httpConnection.disconnect();
        }  catch (UnknownHostException e1) {
            Log.d("MyDebugMsg", "UnknownHostexception in sendHttpGetRequest");
            e1.printStackTrace();
        } catch (Exception ex) {
            Log.d("MyDebugMsg", "Exception in sendHttpGetRequest");
            ex.printStackTrace();
        }
        return image;
    }

    // Get an image from the input stream
    private static Bitmap getImagefromStream(InputStream stream) {
        Bitmap bitmap = null;
        if(stream!=null) {
            bitmap = BitmapFactory.decodeStream(stream);
            try {
                stream.close();
            }catch (IOException e1) {
                Log.d("MyDebugMsg", "IOException in getImagefromStream()");
                e1.printStackTrace();
            }
        }
        return bitmap;
    }

    // Get a string from an input stream
    private static String getStringfromStream(InputStream stream){
        String line, jsonString=null;
        if (stream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder out = new StringBuilder();
            try {
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                reader.close();
                jsonString = out.toString();
            } catch (IOException ex) {
                Log.d("MyDebugMsg", "IOException in downloadJSON()");
                ex.printStackTrace();
            }
        }
        return jsonString;
    }



    // Send json data via HTTP POST Request
    public static void sendHttPostRequest(String urlString, JSONObject json){
        HttpURLConnection httpConnection = null;
        try {
            URL url = new URL(urlString);
            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setDoOutput(true);
            httpConnection.setChunkedStreamingMode(0);

            OutputStreamWriter out = new OutputStreamWriter(httpConnection.getOutputStream());
            out.write(json.toString());
            out.close();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String line;
                while ((line = reader.readLine()) != null) {
                    Log.d("MyDebugMsg:PostRequest", line);  // for debugging purpose
                }
                reader.close();
                Log.d("MyDebugMsg:PostRequest", "POST request returns ok");
            } else Log.d("MyDebugMsg:PostRequest", "POST request returns error");
        } catch (Exception ex) {
            Log.d("MyDebugMsg", "Exception in sendHttpPostRequest");
            ex.printStackTrace();
        }

        if (httpConnection != null) httpConnection.disconnect();
    }



    ///second post

    public static void sendHttPostRequestTwo(String urlString, JSONObject json){
        HttpURLConnection httpConnection = null;
        try {
            URL url = new URL(urlString);
            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setDoOutput(true);
            httpConnection.setChunkedStreamingMode(0);

            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestMethod("POST");
            httpConnection.connect();

            byte[] outputBytes = json.toString().getBytes("UTF-8");
            OutputStream os = httpConnection.getOutputStream();
            os.write(outputBytes);

            os.close();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String line;
                while ((line = reader.readLine()) != null) {
                    Log.d("MyDebugMsg:PostRequest", line);  // for debugging purpose
                }
                reader.close();
                Log.d("MyDebugMsg:PostRequest", "POST request returns ok");
            } else Log.d("MyDebugMsg:PostRequest", "POST request returns error");
        } catch (Exception ex) {
            Log.d("MyDebugMsg", "Exception in sendHttpPostRequest");
            ex.printStackTrace();
        }

        if (httpConnection != null) httpConnection.disconnect();
    }

}

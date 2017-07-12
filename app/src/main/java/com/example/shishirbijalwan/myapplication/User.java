package com.example.shishirbijalwan.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by shishirbijalwan on 4/13/17.
 */

public class User {
    public String name;
    public String password;
    //temporary information
    public String tempUser;
    public String tempPassword;
    public String tempName;
    public String tempDate;
    public String email;
    private Utility utility;
    private String url;
    public ArrayList<DiaryEntry> Diaryentries;
    public ArrayList<Location> locations;
    private Location detailViewLocation;
    private static User instance=null;
    public String temperature;
  public Bitmap profilepicture;
    public String profilePicString;


    //constructor
    private User() {
        utility = new Utility();
        url = "http://ec2-34-205-50-71.compute-1.amazonaws.com:9000/";
        Diaryentries = new ArrayList<>();
        locations = new ArrayList<>();
        email = "";
        password = "";
        temperature="74";
        profilepicture=null;
    }

    public static void setInstance()
    {
        instance=null;
    }
    public static User getInstance(){
        if(instance==null)
            instance=new User();
        return instance;
    }

    //function to create new user

    boolean createUser() {
        password="";
        boolean check = validateUser();
        if (check)
            return false;
        else {


            HashMap sendObject = new HashMap();
            sendObject.put("name", tempName);
            sendObject.put("email", tempUser);
            sendObject.put("password", tempPassword);
            sendObject.put("DOB", tempDate);
            sendObject.put("image",null);

            JSONObject js = new JSONObject(sendObject);
            utility.sendHttPostRequestTwo(url + "newUser", js);

        }


        return true;
    }


    // function to get user Information and password

    boolean validateUser() {


        getUserInformation(tempUser);

        if (password.length() != 0)
            return true;


        return false;
    }

    public void getUserInformation(String emailid) {

        String receivedMsg = utility.downloadJSONusingHTTPGetRequest(url + "getUserInfo/" + emailid);
        System.out.println(receivedMsg);
        try {
            JSONArray jsonArray = new JSONArray(receivedMsg);
            if (jsonArray.length() == 0)
                return;

            JSONObject movieJsonObj = (JSONObject) jsonArray.get(0);
            name = (String) movieJsonObj.get("name");
            email = emailid;
            password = (String) movieJsonObj.get("password");
            profilepicture=getBitmapFromString((String) movieJsonObj.get("image"));

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    //function to get Diary entries of user
    public void getAllDiaryEntries() {
        Diaryentries.clear();
        String receivedMsg = utility.downloadJSONusingHTTPGetRequest(url + "getDiaryEntries/" + email);
        System.out.println(receivedMsg);
        try {
            JSONArray jsonArray = new JSONArray(receivedMsg);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject movieJsonObj = (JSONObject) jsonArray.get(i);
                String DiaryEntry = (String) movieJsonObj.get("data");
                String title = (String) movieJsonObj.get("title");

                com.example.shishirbijalwan.myapplication.DiaryEntry sr= new DiaryEntry();
                sr.data=DiaryEntry;
                sr.Title=title;
                Diaryentries.add(sr);
            }


        } catch (Exception e) {

            System.out.println(e.getMessage());
        }




    }

    //sort the diary entries
    public  void sortDiaryEntry(){
        Collections.sort(Diaryentries, new Comparator<DiaryEntry>(){
            public int compare(DiaryEntry d1, DiaryEntry d2){
                return d1.Title.compareTo(d2.Title);
            }
        });
    }

    //delete the diaryentry
    public void deleteDiaryEntry(int position){

        HashMap hs= new HashMap<>();

        hs.put("title",Diaryentries.get(position).Title);
        JSONObject js= new JSONObject(hs);
        utility.sendHttPostRequestTwo(url + "deleteDiaryEntry", js);

        Diaryentries.remove(position);
    }

    //function to get all locations
    public void getAlllocations() {
        locations.clear();
        String receivedMsg = utility.downloadJSONusingHTTPGetRequest(url + "getAllLocation");
        System.out.println(receivedMsg);
        try {
            JSONArray jsonArray = new JSONArray(receivedMsg);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject movieJsonObj = (JSONObject) jsonArray.get(i);
                Location temLocation = new Location();

                temLocation.locationName = (String) movieJsonObj.get("location");
                temLocation.Description = (String) movieJsonObj.get("description");
                temLocation.ImageUrl = (String) movieJsonObj.get("imageUrl");
                temLocation.VideoUrl = (String) movieJsonObj.get("video");
                temLocation.Logitute = Double.parseDouble(movieJsonObj.get("log").toString());
                temLocation.Latitue = Double.parseDouble(movieJsonObj.get("lat").toString());
                temLocation.rating = Double.parseDouble(movieJsonObj.get("rating").toString());
                temLocation.numberOfUsers = Integer.parseInt(movieJsonObj.get("numberOfUsers").toString());
                locations.add(temLocation);
            }


        } catch (Exception e) {

            System.out.println(e.getMessage());
        }



    }


    //function to getAlocation
    public void getAlocations(int position) {

        String nameOfLocation = locations.get(position).locationName;
        String receivedMsg = utility.downloadJSONusingHTTPGetRequest(url + "getALocation/" + nameOfLocation);
        System.out.println(receivedMsg);
        try {
            JSONArray jsonArray = new JSONArray(receivedMsg);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject movieJsonObj = (JSONObject) jsonArray.get(i);
                Location temLocation = new Location();

                temLocation.locationName = (String) movieJsonObj.get("location");
                temLocation.Description = (String) movieJsonObj.get("description");
                temLocation.ImageUrl = (String) movieJsonObj.get("imageUrl");
                temLocation.VideoUrl = (String) movieJsonObj.get("video");
                temLocation.Logitute = Double.parseDouble(movieJsonObj.get("log").toString());
                temLocation.Latitue = Double.parseDouble(movieJsonObj.get("lat").toString());
                temLocation.rating = Double.parseDouble(movieJsonObj.get("rating").toString());
                temLocation.numberOfUsers = Integer.parseInt(movieJsonObj.get("numberOfUsers").toString());
                detailViewLocation = temLocation;
            }


        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

    }


    //send update image request()

    public void sendImagetoServer(){

       // profilePicString=getStringFromBitmap(profilepicture);

        HashMap sendObject = new HashMap();
        sendObject.put("email", email);
        sendObject.put("image", getStringFromBitmap(profilepicture));

        JSONObject js = new JSONObject(sendObject);
        utility.sendHttPostRequestTwo(url + "updateImage", js);


    }

    private String getStringFromBitmap(Bitmap bitmapPicture) {
 /*
 * This functions converts Bitmap picture to a string which can be
 * JSONified.
 * */
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        bitmapPicture=  Bitmap.createScaledBitmap(bitmapPicture, 60, 60, true);
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    private Bitmap getBitmapFromString(String jsonString) {
/*
* This Function converts the String back to Bitmap
* */
        byte[] decodedString = Base64.decode(jsonString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}

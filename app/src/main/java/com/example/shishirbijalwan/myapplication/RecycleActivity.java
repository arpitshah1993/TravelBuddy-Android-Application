package com.example.shishirbijalwan.myapplication;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

public class RecycleActivity extends AppCompatActivity implements RecycleView.OnMovieButtonClickedListener, Fragment_DetailView.OnDirectionlickedListener {
    Fragment mFragment;
    boolean mtwoPane=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);

        if (savedInstanceState != null) {
            if (getSupportFragmentManager().getFragment(savedInstanceState, "recyclerFrag") != null) {
                mFragment = getSupportFragmentManager().getFragment(savedInstanceState, "recyclerFrag");
            } else {
                mFragment = RecycleView.newInstance();
            }
        } else {
            mFragment = RecycleView.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, mFragment)
                    .commit();
        }

        if(findViewById(R.id.detail_container)!=null)
            mtwoPane=true;


        CookieSyncManager.createInstance(getApplicationContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();


    }

    @Override
    public void movieButtonClicked(int position, Location movie) {
        if(mtwoPane)
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container,
                    Fragment_DetailView.newInstance(movie)).addToBackStack(null).commit();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                    Fragment_DetailView.newInstance(movie)).addToBackStack(null).commit();
    }

    @Override
    public void directionButtonClicked(double log, double lat) {
        Intent i=new Intent(this, MapsActivity.class);
        double[] array=new double[]{lat,log};
        Bundle bundle = new Bundle();
        bundle.putDoubleArray("LocationAT",array);
//Add your data to bundle
        //bundle.putSerializable("Location",);
//Add the bundle to the intent
        i.putExtras(bundle);
        startActivity(i);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    setResult(0);
       finish();
      //  System.exit(0);
//        super.finish();
       // android.os.Process.killProcess(android.os.Process.myPid());
    }


}

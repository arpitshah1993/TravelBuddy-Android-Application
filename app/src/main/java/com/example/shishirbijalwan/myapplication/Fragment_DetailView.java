package com.example.shishirbijalwan.myapplication;

/**
 * Created by arpitshah on 2/9/17.
 */

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class Fragment_DetailView extends Fragment {


    //private static String ARG_SECTION_NUMBER = "section_number";

    Location location;
    OnDirectionlickedListener mdirect;
    WebView webView;
    WebChromeClient web;
    private ShareActionProvider mShareActionProvider;

    //static String name=new String();
    public static Fragment_DetailView newInstance(Location location) {
        Fragment_DetailView fragment = new Fragment_DetailView();
     //   name=(String)movie.get("name");
        Bundle args = new Bundle();
        args.putSerializable("Location", location);
        // args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        //fragment.onDestroyView(new Destr);
        return fragment;
    }

    public interface OnDirectionlickedListener{
        public void directionButtonClicked(double log,double lat);
    }

    public Fragment_DetailView() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        mdirect=(OnDirectionlickedListener)getContext();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View rootView = null;
        location = (Location) getArguments().getSerializable("Location");
        View view = inflater.inflate(R.layout.attractions_details, container, false);
     //   int imgId = (Integer) movie.get("image");
        String movieName = location.locationName;
        //String myvideokey="<iframe width=\"400\" height=\"400\" src=\"https://www.youtube.com/embed/pGFEZUHcsSY\" frameborder=\"0\" allowfullscreen></iframe>";
        String myvideokey=location.VideoUrl;
       /* // ImageView img=(ImageView)getActivity().findViewById(R.id.imageView6);
         MyDownLoadAsyncTask task= new MyDownLoadAsyncTask(img);
         task.execute((String) movie.get("url"));
         img.setClickable(false);*/

  /*       TextView textView=(TextView)getActivity().findViewById(R.id.movienametext);
         textView.setText(movieName);*/

        Toolbar toolbar=(Toolbar)view.findViewById(R.id.attaractionDetailToolBar);
        toolbar.setTitle(location.locationName);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        RatingBar ratingBar=(RatingBar)view.findViewById(R.id.ratingBarDetails) ;
        Double rating=location.rating;
        ratingBar.setRating(rating.floatValue());
        TextView tMovieDesc = (TextView) view.findViewById(R.id.movieDesc);
        tMovieDesc.setText(location.Description);
        ImageButton direction=(ImageButton)view.findViewById(R.id.deirectionbtnn);
        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdirect.directionButtonClicked(location.Logitute,location.Latitue);
            }
        });
        webView=(WebView)view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(myvideokey,"text/html","utf-8");
        web=new WebChromeClient();
        webView.setWebChromeClient(web);

        LinearLayout lr= (LinearLayout) view.findViewById(R.id.attractiondetaillayout);
        Bitmap bmImg=decodeSampledBitmapFromResource(getResources(), R.drawable.backgroundone, 200, 200);
        BitmapDrawable background = new BitmapDrawable(bmImg);
        lr.setBackgroundDrawable(background);

        return view;
    }

    @Override
    public void onCreateOptionsMenu (Menu menu , MenuInflater
            inflater ) {
        inflater.inflate (R.menu.task2fragment2menu , menu );
        MenuItem shareItem = ( MenuItem )
                menu.findItem(R.id.action_share);


        mShareActionProvider =(ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent intentShare = new Intent ( Intent.ACTION_SEND );
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_TEXT, ( String )
                location.locationName);
        if( mShareActionProvider!= null && intentShare != null )
            mShareActionProvider.setShareIntent(intentShare);
        super.onCreateOptionsMenu(menu ,inflater);
    }


    @Override
    public void onDestroyView() {

       webView.clearCache(true);
       getContext().deleteDatabase("webview.db");
        getContext().deleteDatabase("webviewCache.db");

        CookieSyncManager.createInstance(getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        super.onDestroyView();
      //  webView.destroy();
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
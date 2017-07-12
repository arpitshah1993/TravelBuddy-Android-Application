package com.example.shishirbijalwan.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WelcomeActivity extends AppCompatActivity {
    ViewAnimator viewAnimator1;
    private int[] id;
    //=new int[3];
    //id[0]= R.drawable.abcdf;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    int prevPos=0;
    private Button btnSkip, btnNext;//,btnPrev,btnDone;
    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {


        @Override
        public void onPageSelected(int position) {
            if(position>prevPos)
            AnimationFactory.flipTransition(viewAnimator1, AnimationFactory.FlipDirection.RIGHT_LEFT);
            else
                AnimationFactory.flipTransition(viewAnimator1, AnimationFactory.FlipDirection.LEFT_RIGHT);
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == 3 - 1) {
                // if (position == 3 - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
            prevPos=position;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private PrefManager prefManager;

    //com.example.shishirbijalwan.a03_sbijalwa.MovieData MovieData=new MovieData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
/*  This is for the first time welcome intro!!
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }
*/

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);
        viewAnimator1 = (ViewAnimator) this.findViewById(R.id.animator1);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);
        // btnPrev=(Button) findViewById(R.id.btn_prev);
        // btnDone=(Button) findViewById(R.id.btn_done);
        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome1};
        id=new int[]{
                R.drawable.profile,R.drawable.ambulance,R.drawable.backgroundone
        };

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setCurrentItem(0);

        customizeViewPager(); //Animation
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });
//       // btnDone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                launchHomeScreen();
//            }
//        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < 3) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });

//        btnPrev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // checking for last page
//                // if last page home screen will be launched
//                int current = getItem(-1);
//                if (current < 3) {
//                    // move to next screen
//                    viewPager.setCurrentItem(current);
//                } else {
//                    launchHomeScreen();
//                }
//            }
//        });
    }

    private void customizeViewPager(){

        viewPager.setPageTransformer(false,new ViewPager.PageTransformer(){
            @Override
            public void transformPage(View view, float position){
                if (position < 0) {
                    view.setScrollX((int)((float)(view.getWidth()) * position));
                } else if (position > 0) {
                    view.setScrollX(-(int) ((float) (view.getWidth()) * -position));
                } else {
                    view.setScrollX(0);
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int i,float v,int i2){
            }

            @Override
            public void onPageSelected(int i)
            {
            }
            @Override
            public void onPageScrollStateChanged(int i)
            {
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[3];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage%3]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage%3].setTextColor(colorsActive[currentPage%3]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, LandingPage.class));
        finish();
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.welcome1, container, false);

            switch((position%3)){
                case 0:
                    view.setBackgroundColor(Color.parseColor("#f64c73"));
                    break;
                case 1:
                    view.setBackgroundColor(Color.parseColor("#20d2bb"));
                    break;
                case 2:
                    view.setBackgroundColor(Color.parseColor("#3395ff"));
                    break;
            }

            ImageView img=(ImageView)view.findViewById(R.id.img);
            img.setImageResource(id[position]);
            //txt.setText(position);
            container.addView(view);

            return view;
        }




        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
        }
    }
   /* private class MyDownLoadAsyncTask extends AsyncTask<String,Void,Bitmap> {
        private final WeakReference<ImageView> imageViewReference;


        public MyDownLoadAsyncTask(ImageView img) {
            imageViewReference = new WeakReference<ImageView>(img);

        }

        @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap bitmap = null;
            for (String url : params) {
                bitmap = MyUtility.downloadImageusingHTTPGetRequest(url);
                // if(bitmap!=null)


            }
            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {

                final ImageView img = imageViewReference.get();
                if (img != null)
                    img.setImageBitmap(bitmap);
            }
        }
    }*/

}

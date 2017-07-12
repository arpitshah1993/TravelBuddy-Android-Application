package com.example.shishirbijalwan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class DiaryRecycleActivity extends AppCompatActivity implements DiaryRecycleView.OnDiaryButtonClickedListener {
    Fragment mFragment;
    boolean mtwoPane=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        if (savedInstanceState != null) {
            if (getSupportFragmentManager().getFragment(savedInstanceState, "DrecyclerFrag") != null) {
                mFragment = getSupportFragmentManager().getFragment(savedInstanceState, "DrecyclerFrag");
            } else {
                mFragment = DiaryRecycleView.newInstance();
            }
        } else {
            mFragment = DiaryRecycleView.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, mFragment)
                    .commit();
        }

        if(findViewById(R.id.detail_container)!=null)
            mtwoPane=true;

    }


    @Override
    public void diaryButtonClicked(int position, DiaryEntry diaryEntry) {
        if(mtwoPane)
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container,
                    Diary_DetailView.newInstance(diaryEntry)).addToBackStack(null).commit();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                    Diary_DetailView.newInstance(diaryEntry)).addToBackStack(null).commit();
    }

    @Override
    public void newdiaryButtonClicked() {
        Intent i=new Intent(this, newDiaryEntryActivity.class);
        startActivity(i);
    }
}

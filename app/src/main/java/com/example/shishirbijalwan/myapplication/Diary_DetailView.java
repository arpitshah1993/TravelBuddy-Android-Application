package com.example.shishirbijalwan.myapplication;

/**
 * Created by arpitshah on 2/9/17.
 */

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.nio.BufferUnderflowException;
import java.util.Locale;

public class Diary_DetailView extends Fragment {


    //private static String ARG_SECTION_NUMBER = "section_number";

    DiaryEntry diaryEntry;
    TextToSpeech tt;
    // Button speak;

    // OnDiaryClickedListener mdirect;
    //WebView webView;

    public Diary_DetailView() {
    }

    //static String name=new String();
    public static Diary_DetailView newInstance(DiaryEntry diaryEntry) {
        Diary_DetailView fragment = new Diary_DetailView();
        //   name=(String)movie.get("name");
        Bundle args = new Bundle();
        args.putSerializable("DiaryEntry", diaryEntry);
        // args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        tt = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR)
                    tt.setLanguage(new Locale("en"));
            }
        });
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View rootView = null;
        diaryEntry = (DiaryEntry) getArguments().getSerializable("DiaryEntry");
        View view = inflater.inflate(R.layout.diary_details_view, container, false);
        //speak=(Button)view.findViewById(R.id.speakButton);

        TextView title = (TextView) view.findViewById(R.id.diaryTitle);
        final TextView detail = (TextView) view.findViewById(R.id.edittextDiaryDetail);
        detail.setMovementMethod(new ScrollingMovementMethod());
        title.setText(diaryEntry.Title);
        detail.setText(diaryEntry.data);


        Toolbar toolbar = (Toolbar) view.findViewById(R.id.DiaryDetailToolBar);
        toolbar.setTitle("Old memory");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        ImageButton speakButton = (ImageButton) view.findViewById(R.id.speakerButtonDiaryDetail);
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tt.speak(detail.getText(), TextToSpeech.QUEUE_FLUSH,null,null);
                tt.speak(detail.getText(), TextToSpeech.QUEUE_FLUSH,null,null);
            }
        });



        YoYo.with(Techniques.RotateIn)
                .duration(1000)
                .repeat(1)
                .playOn(view.findViewById(R.id.edittextDiaryDetail));



        return view;
    }

//    public void speak(View v){
//
//        String str= diaryEntry.data;
//
//        tt.speak(str, TextToSpeech.QUEUE_FLUSH,null,null);
//    }


}
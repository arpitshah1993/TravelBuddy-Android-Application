package com.example.shishirbijalwan.myapplication;


import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecycleView extends Fragment {

    static RecycleAdapter recycleAdapter;
    //static MovieData movieData;
    RecyclerView mRecycleView;
    LinearLayoutManager mLayOutManager;
    OnMovieButtonClickedListener mListener;
    public RecycleView() {///movieData=new MovieData();}
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListener = (OnMovieButtonClickedListener) getContext();

       // ImageView img = (ImageView)findViewById(R.id.hme);

    }

    public static RecycleView newInstance() {
        RecycleView fragment = new RecycleView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnMovieButtonClickedListener{
        public void movieButtonClicked(int position, Location location);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.recycleview_fragment,container,false);
        rootView.setBackgroundResource(R.drawable.animation_gif);

        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) rootView.getBackground();

        // Start the animation (looped playback by default).
        frameAnimation.start();
        //  movieData=new MovieData();

        //animation
//        Drawable background=rootView.getBackground();
//
//        AnimationDrawable anim = (AnimationDrawable)background;
//        anim.start();

        mRecycleView =(RecyclerView)rootView.findViewById(R.id.cardList);
        mRecycleView.setHasFixedSize(true);
        mLayOutManager= new LinearLayoutManager(rootView.getContext());
        mRecycleView.setLayoutManager(mLayOutManager);
        //  mListener = (OnMovieButtonClickedListener) rootView.getContext();
        //  mListener = (OnMovieButtonClickedListener) rootView.getContext();
        recycleAdapter = new RecycleAdapter(rootView.getContext(), User.getInstance().locations);
        mRecycleView.setAdapter(recycleAdapter);
        recycleAdapter.setOnClickListener(new RecycleAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "Pos: " + position, Toast.LENGTH_LONG).show();
                mListener.movieButtonClicked(position, User.getInstance().locations.get(position));
                // String currId  = (String)movie.getItem(position).get("id");
                //MyDownLoadJsonAsyncTask downLoadJsonAsyncTask1= new MyDownLoadJsonAsyncTask(myBaseAdapter,currId,5);
                //downLoadJsonAsyncTask1.execute(MovieData.PHP_SERVER);
            }
        });
        MyDownLoadJsonAsyncTask downLoadJsonAsyncTask= new MyDownLoadJsonAsyncTask(recycleAdapter,"",1);
        downLoadJsonAsyncTask.execute("");

        Toolbar toolbar= (Toolbar) rootView.findViewById(R.id.Locationfragment1toolbar);
        toolbar.setTitle("Tourist Locations");

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        setHasOptionsMenu(true);
        return rootView;

    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }
    private class MyDownLoadJsonAsyncTask extends AsyncTask<String,Void,Location> {
        private final WeakReference<RecycleAdapter> adapterReference;
        String info=new String();
        int casee=0;

        public  MyDownLoadJsonAsyncTask(RecycleAdapter adapter,String infoo, int stringCase){
            adapterReference= new WeakReference<RecycleAdapter>(adapter);
            info=infoo;
            casee=stringCase;
        }

        @Override
        protected Location doInBackground(String... params) {

            Location threadMovieData= new Location();
            User.getInstance().getAlllocations();
            return threadMovieData;
        }

        @Override
        protected  void onPostExecute(Location movieDataThread){


            if (adapterReference != null) {
                final RecycleAdapter recap = adapterReference.get();
                if (recap != null) {
                    recap.notifyDataSetChanged();
                }
            }
        }

    }


}




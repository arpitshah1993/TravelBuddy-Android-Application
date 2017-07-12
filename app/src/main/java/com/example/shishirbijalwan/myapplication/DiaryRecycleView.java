//package com.example.shishirbijalwan.myapplication;
//
//
//import android.content.Intent;
//import android.graphics.drawable.AnimationDrawable;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.SearchView;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.CookieManager;
//import android.webkit.CookieSyncManager;
//import android.widget.Toast;
//
//import com.melnykov.fab.FloatingActionButton;
//
//import java.lang.ref.WeakReference;
//import java.util.HashMap;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class DiaryRecycleView extends Fragment {
//
//    static DiaryRecycleAdapter recycleAdapter;
//    //static MovieData movieData;
//    RecyclerView mRecycleView;
//    LinearLayoutManager mLayOutManager;
//    OnDiaryButtonClickedListener mListener;
//    public DiaryRecycleView() {///movieData=new MovieData();}
//    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mListener = (OnDiaryButtonClickedListener) getContext();
//
//
//        //  movieData=new MovieData();
//    }
//
//    public static DiaryRecycleView newInstance() {
//        DiaryRecycleView fragment = new DiaryRecycleView();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public interface OnDiaryButtonClickedListener{
//        public void diaryButtonClicked(int position, DiaryEntry location);
//        public void newdiaryButtonClicked();
//
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView=inflater.inflate(R.layout.recycleview_fragment_diary,container,false);
//        rootView.setBackgroundResource(R.drawable.animation_gif);
//        // Get the background, which has been compiled to an AnimationDrawable object.
//        AnimationDrawable frameAnimation = (AnimationDrawable) rootView.getBackground();
//        // Start the animation (looped playback by default).
//        frameAnimation.start();
//
//        mRecycleView =(RecyclerView)rootView.findViewById(R.id.cardList);
//        mRecycleView.setHasFixedSize(true);
//        mLayOutManager= new LinearLayoutManager(rootView.getContext());
//        mRecycleView.setLayoutManager(mLayOutManager);
//        //  mListener = (OnMovieButtonClickedListener) rootView.getContext();
//        //  mListener = (OnMovieButtonClickedListener) rootView.getContext();
//        recycleAdapter = new DiaryRecycleAdapter(rootView.getContext(), User.getInstance().Diaryentries);
//        mRecycleView.setAdapter(recycleAdapter);
//        recycleAdapter.setOnClickListener(new DiaryRecycleAdapter.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(View view, int position) {
//                Toast.makeText(getContext(), "Pos: " + position, Toast.LENGTH_LONG).show();
//                mListener.diaryButtonClicked(position, User.getInstance().Diaryentries.get(position));
//                // String currId  = (String)movie.getItem(position).get("id");
//                //MyDownLoadJsonAsyncTask downLoadJsonAsyncTask1= new MyDownLoadJsonAsyncTask(myBaseAdapter,currId,5);
//                //downLoadJsonAsyncTask1.execute(MovieData.PHP_SERVER);
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//                Toast.makeText(getContext(),"Item Long click",Toast.LENGTH_LONG).show();
//                ActionBarCallBack actionBarCallBack= new ActionBarCallBack(position);
//                getActivity().startActionMode( actionBarCallBack);
//
//
//            }
//
//        });
//        MyDownLoadJsonAsyncTask downLoadJsonAsyncTask= new MyDownLoadJsonAsyncTask(recycleAdapter,"",1);
//        downLoadJsonAsyncTask.execute("");
//
//        Toolbar toolbar=(Toolbar)rootView.findViewById(R.id.task3fragment1toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        setHasOptionsMenu(true);
//
//        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
//        fab.attachToRecyclerView(mRecycleView);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.newdiaryButtonClicked();
//            }
//        });
//
//        return rootView;
//
//    }
//
//    //on create option menu
//    @Override
//    public void onCreateOptionsMenu (Menu menu , MenuInflater
//            inflater ) {
//        if( menu.findItem (R.id.searchoption ) == null )
//            inflater.inflate (R.menu.diaryentrymenu , menu );
//        SearchView search = ( SearchView )
//                menu.findItem(R.id.searchoption).getActionView();
//
//        if(search!=null){
//
//
//            search.setOnQueryTextListener( new SearchView.OnQueryTextListener(){
//
//
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//              //      LoadByRatingAsyncTask loadByRatingAsyncTask= new LoadByRatingAsyncTask(recyclerAdapter);
//                    //  String url=MovieData.PHP_SERVER+"movies/";
//                //    String url=MovieData.PHP_SERVER+"movies/rating/"+query;
//                  //  Log.d("Making load async call",url);
//                    //loadByRatingAsyncTask.execute(url);
//                    return true;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    return true;
//                }
//            });
//        }
//
//        super.onCreateOptionsMenu(menu ,inflater);
//    }
//
//
//
//
//    @Override
//    public void onSaveInstanceState(Bundle outState){
//        super.onSaveInstanceState(outState);
//    }
//
//    private class MyDownLoadJsonAsyncTask extends AsyncTask<String,Void,Location> {
//        private final WeakReference<DiaryRecycleAdapter> adapterReference;
//        String info=new String();
//        int casee=0;
//
//        public  MyDownLoadJsonAsyncTask(DiaryRecycleAdapter adapter,String infoo, int stringCase){
//            adapterReference= new WeakReference<DiaryRecycleAdapter>(adapter);
//            info=infoo;
//            casee=stringCase;
//        }
//
//        @Override
//        protected Location doInBackground(String... params) {
//
//            Location threadMovieData= new Location();
//            User.getInstance().getAllDiaryEntries();
////            for(String url:params){
////                //add
////                if(casee==3){
////                    Integer i=Integer.parseInt(info);
////                    HashMap movieD=movieData.getItem(Integer.valueOf(i));
////                    JSONObject movieDJson=new JSONObject(movieD);
////                    MyUtility.sendHttPostRequest(url+"/add",movieDJson);
////                    //threadMovieData.downloadMovieDataJson(url+"/movies/");
////                }
////                //by id
////                else if(casee==5){
////                    // threadMovieData.downloadMovieDataJson(url+"/movies/id/"+info);
////                }
////                //delete
////                else if(casee==4){
////                    Integer i=Integer.parseInt(info);
////                    HashMap movieD=movieData.getItem(Integer.valueOf(i));
////                    JSONObject movieDJson=new JSONObject(movieD);
////                    MyUtility.sendHttPostRequest(url+"/delete/id",movieDJson);
////                    // threadMovieData.downloadMovieDataJson(url+"/movies/");
////                }
////                //getMovies
////                // else if(casee==1)
////                //threadMovieData.downloadMovieDataJson(url+"/movies/");
////                //rating
////                //else if(casee==2)
////                // threadMovieData.downloadMovieDataJson(url+"/movies/rating/"+info);
////                //  System.out.println();
////                // if(bitmap!=null;
////            }
//            return threadMovieData;
//        }
//
//        @Override
//        protected  void onPostExecute(Location movieDataThread){
////            if(casee==5){
////                //HashMap<String, ?> m = (HashMap<String, ?>) movie.getItem(position);
////                mListener.movieButtonClicked(0, (HashMap<String, ?>)movieDataThread.moviesList.get(0));
////                // myBaseAdapter.notifyItemChanged(position);
////            }
////            else {
////                movieData.moviesList.clear();
////
////                Toast.makeText(getContext(), "length" + movieDataThread.getSize(), Toast.LENGTH_LONG).show();
////                for (int i = 0; i < movieDataThread.getSize(); i++) {
////                    movieData.moviesList.add(movieDataThread.moviesList.get(i));
////
////                }
//
//            if (adapterReference != null) {
//                final DiaryRecycleAdapter recap = adapterReference.get();
//                if (recap != null) {
//                    recap.notifyDataSetChanged();
//                }
//            }
//        }
//
//    }
//
//
//    class deleteDiaryEntry extends AsyncTask<String, Void, Boolean> {
//        int position;
//        deleteDiaryEntry(int pos)
//        {
//            position=pos;
//        }
//
//        @Override
//        protected Boolean doInBackground(String... params) {
//
//            User user=User.getInstance();
//            user.deleteDiaryEntry(position);
//           // Boolean bol = user.createUser();
//            return true;
//        }
//
//        // Get a string from an input stream
//
//        @Override
//        protected void onPostExecute(Boolean result) {
//            super.onPostExecute(result);
//            recycleAdapter.notifyItemRemoved(position);
//        }
//    }
//
//    // action bar call back
//
//    ///
//    public class ActionBarCallBack implements android.view.ActionMode.Callback {
//        int position;
//        public ActionBarCallBack(int num){
//            position=num;
//        }
//
//        @Override
//        public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
//            mode.getMenuInflater().inflate(R.menu.longclickmenu , menu );
//
//            return true;
//        }
//
//        @Override
//        public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
//          //  HashMap movie= (HashMap) mv.getItem(position);
//           // mode.setTitle((String)movie.get("name"));
//            return false;
//        }
//
//        @Override
//        public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
//            int id= item.getItemId();
//            User user;
//            switch (id){
//                case R.id.actiondelete:
//                     new deleteDiaryEntry(position).execute();
////                    user=User.getInstance();
////                    user.deleteDiaryEntry(position);
////                    recycleAdapter.notifyItemRemoved(position);
//                   // mv.deleteAtIndex(position);
//                    //recyclerAdapter.notifyItemRemoved(position);
//                    mode.finish();
//                    break;
//                case R.id.actionsort:
//                    user=User.getInstance();
//                    user.sortDiaryEntry();
//                    recycleAdapter.notifyDataSetChanged();
//                    mode.finish();
//                    break;
//                default:
//                    break;
//
//            }
//            return false;
//        }
//
//        @Override
//        public void onDestroyActionMode(android.view.ActionMode mode) {
//
//        }
//    }
//
//
//
//}
//
//
//

package com.example.shishirbijalwan.myapplication;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;
import android.support.v7.graphics.Palette;

import com.melnykov.fab.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryRecycleView extends Fragment {

    static DiaryRecycleAdapter recycleAdapter;
    //static MovieData movieData;
    RecyclerView mRecycleView;
    LinearLayoutManager mLayOutManager;
    OnDiaryButtonClickedListener mListener;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    public DiaryRecycleView() {///movieData=new MovieData();}
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListener = (OnDiaryButtonClickedListener) getContext();


        //  movieData=new MovieData();
    }

    public static DiaryRecycleView newInstance() {
        DiaryRecycleView fragment = new DiaryRecycleView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnDiaryButtonClickedListener{
        public void diaryButtonClicked(int position, DiaryEntry location);
        public void newdiaryButtonClicked();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.recycleview_fragment_diary,container,false);
                rootView.setBackgroundResource(R.drawable.animation_gif);
        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) rootView.getBackground();
        // Start the animation (looped playback by default).
        frameAnimation.start();
        mRecycleView =(RecyclerView)rootView.findViewById(R.id.cardList);
        mRecycleView.setHasFixedSize(true);
        mLayOutManager= new LinearLayoutManager(rootView.getContext());
        mRecycleView.setLayoutManager(mLayOutManager);
        //  mListener = (OnMovieButtonClickedListener) rootView.getContext();
        //  mListener = (OnMovieButtonClickedListener) rootView.getContext();
        recycleAdapter = new DiaryRecycleAdapter(rootView.getContext(), User.getInstance().Diaryentries);
        mRecycleView.setAdapter(recycleAdapter);
        recycleAdapter.setOnClickListener(new DiaryRecycleAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "Pos: " + position, Toast.LENGTH_LONG).show();
                mListener.diaryButtonClicked(position, User.getInstance().Diaryentries.get(position));
                // String currId  = (String)movie.getItem(position).get("id");
                //MyDownLoadJsonAsyncTask downLoadJsonAsyncTask1= new MyDownLoadJsonAsyncTask(myBaseAdapter,currId,5);
                //downLoadJsonAsyncTask1.execute(MovieData.PHP_SERVER);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(),"Item Long click",Toast.LENGTH_LONG).show();
                ActionBarCallBack actionBarCallBack= new ActionBarCallBack(position);
                getActivity().startActionMode( actionBarCallBack);


            }

        });
        MyDownLoadJsonAsyncTask downLoadJsonAsyncTask= new MyDownLoadJsonAsyncTask(recycleAdapter,"",1);
        downLoadJsonAsyncTask.execute("");

        Toolbar toolbar=(Toolbar)rootView.findViewById(R.id.toolbar);
        // collapsingToolbar.setTitle("Title");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.user_name));


        setHasOptionsMenu(true);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecycleView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.newdiaryButtonClicked();
            }
        });
        dynamicToolbarColor();

        toolbarTextAppernce();
        return rootView;

    }

    private void dynamicToolbarColor() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.profile);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //      int mutedColor = palette.getVibrantSwatch().getRgb();

                //    collapsingToolbarLayout.setBackgroundColor(mutedColor);

                //collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(getResources().getColor(R.color.blue)));
                //collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(getResources().getColor(R.color.blue)));
            }
        });
    }

    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }
    //on create option menu
    @Override
    public void onCreateOptionsMenu (Menu menu , MenuInflater
            inflater ) {
        if( menu.findItem (R.id.searchoption ) == null )
            inflater.inflate (R.menu.diaryentrymenu , menu );
        SearchView search = ( SearchView )
                menu.findItem(R.id.searchoption).getActionView();

        if(search!=null){


            search.setOnQueryTextListener( new SearchView.OnQueryTextListener(){


                @Override
                public boolean onQueryTextSubmit(String query) {
                    //      LoadByRatingAsyncTask loadByRatingAsyncTask= new LoadByRatingAsyncTask(recyclerAdapter);
                    //  String url=MovieData.PHP_SERVER+"movies/";
                    //    String url=MovieData.PHP_SERVER+"movies/rating/"+query;
                    //  Log.d("Making load async call",url);
                    //loadByRatingAsyncTask.execute(url);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }
            });
        }

        super.onCreateOptionsMenu(menu ,inflater);
    }




    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    private class MyDownLoadJsonAsyncTask extends AsyncTask<String,Void,Location> {
        private final WeakReference<DiaryRecycleAdapter> adapterReference;
        String info=new String();
        int casee=0;

        public  MyDownLoadJsonAsyncTask(DiaryRecycleAdapter adapter,String infoo, int stringCase){
            adapterReference= new WeakReference<DiaryRecycleAdapter>(adapter);
            info=infoo;
            casee=stringCase;
        }

        @Override
        protected Location doInBackground(String... params) {

            Location threadMovieData= new Location();
            User.getInstance().getAllDiaryEntries();
//            for(String url:params){
//                //add
//                if(casee==3){
//                    Integer i=Integer.parseInt(info);
//                    HashMap movieD=movieData.getItem(Integer.valueOf(i));
//                    JSONObject movieDJson=new JSONObject(movieD);
//                    MyUtility.sendHttPostRequest(url+"/add",movieDJson);
//                    //threadMovieData.downloadMovieDataJson(url+"/movies/");
//                }
//                //by id
//                else if(casee==5){
//                    // threadMovieData.downloadMovieDataJson(url+"/movies/id/"+info);
//                }
//                //delete
//                else if(casee==4){
//                    Integer i=Integer.parseInt(info);
//                    HashMap movieD=movieData.getItem(Integer.valueOf(i));
//                    JSONObject movieDJson=new JSONObject(movieD);
//                    MyUtility.sendHttPostRequest(url+"/delete/id",movieDJson);
//                    // threadMovieData.downloadMovieDataJson(url+"/movies/");
//                }
//                //getMovies
//                // else if(casee==1)
//                //threadMovieData.downloadMovieDataJson(url+"/movies/");
//                //rating
//                //else if(casee==2)
//                // threadMovieData.downloadMovieDataJson(url+"/movies/rating/"+info);
//                //  System.out.println();
//                // if(bitmap!=null;
//            }
            return threadMovieData;
        }

        @Override
        protected  void onPostExecute(Location movieDataThread){
//            if(casee==5){
//                //HashMap<String, ?> m = (HashMap<String, ?>) movie.getItem(position);
//                mListener.movieButtonClicked(0, (HashMap<String, ?>)movieDataThread.moviesList.get(0));
//                // myBaseAdapter.notifyItemChanged(position);
//            }
//            else {
//                movieData.moviesList.clear();
//
//                Toast.makeText(getContext(), "length" + movieDataThread.getSize(), Toast.LENGTH_LONG).show();
//                for (int i = 0; i < movieDataThread.getSize(); i++) {
//                    movieData.moviesList.add(movieDataThread.moviesList.get(i));
//
//                }

            if (adapterReference != null) {
                final DiaryRecycleAdapter recap = adapterReference.get();
                if (recap != null) {
                    recap.notifyDataSetChanged();
                }
            }
        }

    }


    class deleteDiaryEntry extends AsyncTask<String, Void, Boolean> {
        int position;
        deleteDiaryEntry(int pos)
        {
            position=pos;
        }

        @Override
        protected Boolean doInBackground(String... params) {

            User user=User.getInstance();
            user.deleteDiaryEntry(position);
            // Boolean bol = user.createUser();
            return true;
        }

        // Get a string from an input stream

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            recycleAdapter.notifyItemRemoved(position);
        }
    }

    // action bar call back

    ///
    public class ActionBarCallBack implements android.view.ActionMode.Callback {
        int position;
        public ActionBarCallBack(int num){
            position=num;
        }

        @Override
        public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.longclickmenu , menu );

            return true;
        }

        @Override
        public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
            //  HashMap movie= (HashMap) mv.getItem(position);
            // mode.setTitle((String)movie.get("name"));
            return false;
        }

        @Override
        public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
            int id= item.getItemId();
            User user;
            switch (id){
                case R.id.actiondelete:
                    new deleteDiaryEntry(position).execute();
//                    user=User.getInstance();
//                    user.deleteDiaryEntry(position);
//                    recycleAdapter.notifyItemRemoved(position);
                    // mv.deleteAtIndex(position);
                    //recyclerAdapter.notifyItemRemoved(position);
                    mode.finish();
                    break;
                case R.id.actionsort:
                    user=User.getInstance();
                    user.sortDiaryEntry();
                    recycleAdapter.notifyDataSetChanged();
                    mode.finish();
                    break;
                default:
                    break;

            }
            return false;
        }

        @Override
        public void onDestroyActionMode(android.view.ActionMode mode) {

        }
    }



}




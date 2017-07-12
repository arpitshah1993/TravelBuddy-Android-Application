package com.example.shishirbijalwan.myapplication;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by arpitshah on 4/16/17.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    static OnItemClickListener mitemClickListener;
    private final Context context;
    private  ArrayList<Location> locations;

    public RecycleAdapter(Context context, ArrayList<Location> locations) {
        this.context = context;
        this.locations = locations;
    }

    public void setOnClickListener(OnItemClickListener mListener) {
        mitemClickListener = mListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

    }
    public  class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView movieImage;
        public TextView movieName;
        public TextView movieDesc;
        //  public CheckBox movieCheck;
        public RatingBar ratingBar;
        public TextView movieRating;
        public ImageView menuPop;
        public ViewHolder(View itemView) {
            super(itemView);
            movieImage = (ImageView)itemView.findViewById(R.id.movieImage);
            movieName = (TextView)itemView.findViewById(R.id.movieName);
            movieDesc = (TextView)itemView.findViewById(R.id.movieDesc);
            // movieCheck = (CheckBox)itemView.findViewById(R.id.movieCheck);
            ratingBar=(RatingBar)itemView.findViewById(R.id.ratingBar2);
            movieRating = (TextView)itemView.findViewById(R.id.movieRating);
            menuPop=(ImageView)itemView.findViewById(R.id.imageButton);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mitemClickListener != null) {
                        mitemClickListener.onItemClick(v, getPosition());
                    }
                }
            });
        }
        public void bind( Location location) {
            //  imageView.setImageResource((Integer) movie.get("image"));
          //  String URL=;
            MyDownLoadAsyncTask task1= new MyDownLoadAsyncTask(movieImage);
            task1.execute(location.ImageUrl);
            movieName.setText(location.locationName);
            movieDesc.setText(location.Description);
            //movieCheck.setChecked((Boolean) movie.get("selection"));
            Double rating=location.rating;
            ratingBar.setRating(rating.floatValue()/2);
            movieRating.setText(rating.toString());
            //textView=(TextView)itemView.findViewById(R.id.movieName);
        }
    }
        @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attraction_card, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
    }

    @Override
    public void onBindViewHolder(RecycleAdapter.ViewHolder holder, int position) {
        Location location=User.getInstance().locations.get(position);
        holder.bind(location);
    }


    @Override
    public int getItemCount() {
        return User.getInstance().locations.size();
    }

    private class MyDownLoadAsyncTask extends AsyncTask<String,Void,Bitmap> {
        private final WeakReference<ImageView> imageViewReference;


        public MyDownLoadAsyncTask(ImageView img) {
            imageViewReference = new WeakReference<ImageView>(img);

        }

        @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap bitmap = null;
            for (String url : params) {
                bitmap = Utility.downloadImageusingHTTPGetRequest(url);
                // if(bitmap!=null)


            }
            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {

                final ImageView img = imageViewReference.get();
                if (img != null){
                    Bitmap.createScaledBitmap(bitmap, 40, 40, false);

                    img.setImageBitmap(bitmap);

                }
            }
        }
    }
}

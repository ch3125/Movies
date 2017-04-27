package com.me.movies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Home on 11/22/2016.
 */

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private ArrayList<String> array;
        private int width;
        public ImageAdapter(Context context, ArrayList<String> paths,int x) {

            mContext=context;
            array=paths;
            width=x;
        }

        @Override
        public int getCount() {
            return array.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if(convertView==null){
                imageView=new ImageView(mContext);

            }
            else{
                imageView=(ImageView)convertView;
            }
            Drawable d=resizeDrawable(mContext.getResources().getDrawable(R.drawable.loading));
            Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185"+array.get(position)).resize(width,width).placeholder(d)
                    .into(imageView);
            return imageView;

        }
        private Drawable resizeDrawable(Drawable image){
            Bitmap b=((BitmapDrawable)image).getBitmap();
            Bitmap bitmapResized=Bitmap.createScaledBitmap(b,width,(width),false);
            return new BitmapDrawable(mContext.getResources(),bitmapResized);
        }
    }




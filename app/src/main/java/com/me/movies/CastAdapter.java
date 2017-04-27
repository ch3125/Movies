package com.me.movies;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Home on 11/19/2016.
 */

public class CastAdapter extends BaseAdapter {
    Context mContext;
    private ArrayList<String> image;
    private int width;
    private ArrayList<String> name;
    private ArrayList<String > character;
    boolean list;

    public CastAdapter(Context mContext, ArrayList<String> image, int width, ArrayList<String> name, ArrayList<String> character,boolean list) {
        this.mContext = mContext;
        this.image = image;
        this.width = width;
        this.name = name;
        this.character = character;
        this.list=list;
    }

    @Override
    public int getCount() {
        return image.size();
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

        Viewholder holder;
        if(list==true){
        if(convertView==null){
            LayoutInflater inflater=((Activity)mContext).getLayoutInflater();
            convertView=inflater.inflate(R.layout.activity_row,parent,false);
            holder=new Viewholder();
            holder.nameTextView=(TextView)convertView.findViewById(R.id.firstLine);
            holder.characterTextView=(TextView)convertView.findViewById(R.id.secondLine);
            holder.characterImage=(ImageView)convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }
        else{
            holder=(Viewholder)convertView.getTag();

        }
        Drawable d=resizeDrawable(mContext.getResources().getDrawable(R.drawable.loading));
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185"+image.get(position)).resize(width,(int)(width*1.5)).placeholder(d)
                .into(holder.characterImage);
        holder.nameTextView.setText(name.get(position));
        holder.characterTextView.setText(character.get(position));}
        else{
            if(convertView==null){
                LayoutInflater inflater=((Activity)mContext).getLayoutInflater();
                convertView=inflater.inflate(R.layout.activity_grid_row,parent,false);
                holder=new Viewholder();
                holder.nameTextView=(TextView)convertView.findViewById(R.id.secondLine);
              //  holder.characterTextView=(TextView)convertView.findViewById(R.id.secondLine);
                holder.characterImage=(ImageView)convertView.findViewById(R.id.icon);
                convertView.setTag(holder);
            }
            else{
                holder=(Viewholder)convertView.getTag();

            }
            Drawable d=resizeDrawable(mContext.getResources().getDrawable(R.drawable.loading));
            Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185"+image.get(position)).resize(width,(int)(width*1.5)).placeholder(d)
                    .into(holder.characterImage);
            holder.nameTextView.setText(name.get(position));
          //  holder.characterTextView.setText(character.get(position));

        }
        return convertView;
    }
    private Drawable resizeDrawable(Drawable image){
        Bitmap b=((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized=Bitmap.createScaledBitmap(b,width,(int)(width*1.5),false);
        return new BitmapDrawable(mContext.getResources(),bitmapResized);
    }


    static class Viewholder{
         private TextView nameTextView;
         private TextView characterTextView;
         private ImageView characterImage;


    }
}

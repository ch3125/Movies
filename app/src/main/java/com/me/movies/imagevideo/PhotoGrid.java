package com.me.movies.imagevideo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.GridView;

import com.me.movies.ImageAdapter;
import com.me.movies.MainActivity;
import com.me.movies.R;

import java.util.ArrayList;

public class PhotoGrid extends AppCompatActivity {
    private GridView gridview;
    private ArrayList<String > moviePosters;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_grid);
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if (MainActivity.TABLET) {
            width = size.x / 6;
        } else
            width = (int)(size.x / 2.2);
        ArrayList<String> array = new ArrayList<>();
        ImageAdapter adapter = new ImageAdapter(this, array, width);
        gridview=(GridView)findViewById(R.id.gridview);
        gridview.setColumnWidth(width);
        gridview.setAdapter(adapter);
        moviePosters=new ArrayList<>();
        Intent intent=getIntent();
        if(intent==null){
            Log.e("photogrid","intent not recievedd"
                    );
        }
        if(intent!=null && intent.hasExtra("movieposters")){

            moviePosters=intent.getStringArrayListExtra("movieposters");
            Log.e("movieposters recived",String.valueOf(moviePosters.size()));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageAdapter adapter=new ImageAdapter(this,moviePosters,width);
        gridview.setColumnWidth(width);
        gridview.setAdapter(adapter);
    }
}

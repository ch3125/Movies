package com.me.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Home on 11/19/2016.
 */

public class OverView extends Fragment {
    private TextView tv;
    private static String overview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.activity_overview,container,false);
        tv=(TextView)rootView.findViewById(R.id.over);
        Intent intent=getActivity().getIntent();

        if(intent!=null && intent.hasExtra("overview")){
            overview=intent.getStringExtra("overview");
         //   Log.e("overview: ",overview);
            tv.setText(overview);

        }
        return rootView;
    }
}

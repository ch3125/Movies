package com.me.movies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.me.movies.Rest.ApiClient;
import com.me.movies.Rest.ApiInterface;
import com.me.movies.model.CastMovie;
import com.me.movies.model.CastMoviesResponse;
import com.me.movies.model.CreditsMovie;
import com.me.movies.model.CreditsMoviesResponse;
import com.me.movies.model.Movie;
import com.me.movies.model.MoviesResponse;
import com.me.movies.model.PeopleMoviesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Home on 11/20/2016.
 */

public class Credits extends Fragment implements View.OnClickListener{
    public static Integer castId;
    public static Integer ids;
    static int width;
    private GridView castList;
  //  CastAdapter adapter;
    static String API_KEY = "e649c1ec4f43c9f8ea307ec5aec0e891";
    private static ArrayList<Integer> idlist;
    private static ArrayList<String > overview;
    private static ArrayList<String> title;
    private static ArrayList<String> rating;
    private static ArrayList<String> poster;
    private static ArrayList<String> date;
    private static ArrayList<String> character;
    SharedPref sharedPref=new SharedPref();
    static FloatingActionButton changeList ;
    static FloatingActionButton changeGrid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_movies_fragment, container, false);


        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if (MainActivity.TABLET) {
            width = size.x / 6;
        } else
            width = size.x / 3;
        if (getActivity() != null) {
            final Intent intent = getActivity().getIntent();
            //sharedPref=new SharedPref();
            sharedPref.getValue(getActivity(),"popular");


            if (intent != null && intent.hasExtra("idintent")) {
                Log.e("credits: movie id: ", String.valueOf(intent.getIntExtra("idintent",55)));
                castId =intent.getIntExtra("idintent",10361);
            }
           final ArrayList<String> image = new ArrayList<>();
            final ArrayList<String> names = new ArrayList<>();
            final ArrayList<String> characters = new ArrayList<>();

            castList = (GridView) rootView.findViewById(R.id.gridview);
           CastAdapter adapter = new CastAdapter(getActivity(), image, width, names, characters,sharedPref.getValue(getActivity(),"popular"));
            castList.setColumnWidth(width);
            castList.setAdapter(adapter);
            //initializing buttons
            changeGrid=(FloatingActionButton) rootView.findViewById(R.id.changegrid);
            changeGrid.setOnClickListener(this);
            changeList=(FloatingActionButton) rootView.findViewById(R.id.changelist);
            changeList.setOnClickListener(this);

            castList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent1=new Intent(getActivity(),DetailActivity.class);
                    intent1.putExtra("idintent",idlist.get(position));
                    intent1.putExtra("overview",overview.get(position));
                    intent1.putExtra("title",title.get(position));
                    intent1.putExtra("poster",poster.get(position));
                    intent1.putExtra("rating",date.get(position));
                    startActivity(intent1);
                }
            });


        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    idlist=new ArrayList<Integer>();
        overview=new ArrayList<String>();
        title=new ArrayList<String>();
        poster=new ArrayList<>();
        rating=new ArrayList<>();
        character=new ArrayList<>();
        date=new ArrayList<>();
        if (isNetworkAvailable()) {
            castList.setVisibility(GridView.VISIBLE);
            excute();

        } else {
            Toast.makeText(getActivity(), "No internet connection found", Toast.LENGTH_LONG).show();
            castList.setVisibility(GridView.GONE);
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    private void excute() {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<CreditsMoviesResponse> call = apiService.getMovieCredits(castId, API_KEY);
        call.enqueue(new Callback<CreditsMoviesResponse>() {

            @Override
            public void onResponse(Call<CreditsMoviesResponse> call, Response<CreditsMoviesResponse> response) {
                if(response.body()!=null){
                    List<CreditsMovie> movies = response.body().getCast();
                    Log.e("credits.java", "Number of movie credits received  " + movies.size());
                    if (movies != null) {
                        for (int i = 0; i < movies.size(); i++) {
                            poster.add(movies.get(i).getPosterPath());
                            character.add(movies.get(i).getCharacter());
                            title.add(movies.get(i).getTitle());
                            date.add(movies.get(i).getReleaseDate());
                            idlist.add(movies.get(i).getMovieid());
                            ids=idlist.get(i);

                            excute2();

                        }

                        CastAdapter adapter = new CastAdapter(getActivity(), poster, width, title, character,sharedPref.getValue(getActivity(),"popular"));
                        if(sharedPref.getValue(getActivity(),"popular")==true)
                            castList.setNumColumns(GridView.CHOICE_MODE_SINGLE);
                        else
                            castList.setNumColumns(GridView.AUTO_FIT);
                        castList.setColumnWidth(width);
                        castList.setAdapter(adapter);

                    }


                }}

            @Override
            public void onFailure(Call<CreditsMoviesResponse> call, Throwable t) {
                Log.e("credits fail",t.toString());
            }
        });}
    private void excute2() {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Movie> call = apiService.getMovieDetails(ids, API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                //   List<CastMovie> movies = response.body();
                //Log.d("TAG", "Number of casts received  " + movies.size());
                //  ArrayList<String> image = new ArrayList<String>();
                //ArrayList<String> name = new ArrayList<String>();
                //ArrayList<String> character = new ArrayList<String>();
                if (response.body() != null) {

                  overview.add(response.body().getOverview());

                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("cast.java:",t.toString());
            }
        });

}
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.changelist:
                castList.setNumColumns(0);
                changeGrid.setVisibility(View.VISIBLE);
                changeList.setVisibility(View.GONE);
                sharedPref.save(getActivity(),true,"popular");
                CastAdapter adapter = new CastAdapter(getActivity(), poster, width, title, character,sharedPref.getValue(getActivity(),"popular"));
                castList.setAdapter(adapter);

                return ;
            case R.id.changegrid:
                castList.setNumColumns(GridView.AUTO_FIT);
                castList.setColumnWidth(width);
                sharedPref.save(getActivity(),false,"popular");
                CastAdapter adapter1 = new CastAdapter(getActivity(), poster, width, title, character,sharedPref.getValue(getActivity(),"popular"));
                castList.setAdapter(adapter1);
                changeGrid.setVisibility(View.GONE);
                changeList.setVisibility(View.VISIBLE);
        }

    }}

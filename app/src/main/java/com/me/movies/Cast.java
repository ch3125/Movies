package com.me.movies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.me.movies.model.Movie;
import com.me.movies.model.MoviesResponse;
import com.me.movies.model.PeopleMoviesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cast extends Fragment implements View.OnClickListener {
    public static Integer movieId=55;
    public static Integer Casts=258;
    static int width;
    private GridView castList;
    CastAdapter adapter;
    static String API_KEY = "e649c1ec4f43c9f8ea307ec5aec0e891";

    private ArrayList<Integer> castId = new ArrayList<>();
    private ArrayList<String> character = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> profilePath = new ArrayList<>();
    private ArrayList<String> biography=new ArrayList<>();
    private ArrayList<String > placeOfBirth=new ArrayList<>();
    static FloatingActionButton changeList ;
    static FloatingActionButton changeGrid;
    SharedPref sharedPref=new SharedPref();

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


            if (intent != null && intent.hasExtra("idintent")) {
                Log.e("Cast movie id: ", String.valueOf(intent.getIntExtra("idintent",55)));
                 movieId =intent.getIntExtra("idintent",55);
            }
            ArrayList<String> image = new ArrayList<>();
            final ArrayList<String> names = new ArrayList<>();
            final ArrayList<String> characters = new ArrayList<>();
            sharedPref.getValue(getActivity(),"popular");
           // sharedPref.save(getActivity(),false);

            castList = (GridView) rootView.findViewById(R.id.gridview);
            adapter = new CastAdapter(getActivity(), image, width, names, characters,sharedPref.getValue(getActivity(),"popular"));
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


                    Intent intent1=new Intent(getActivity(),CastDetail.class);
                    intent1.putExtra("idintent",castId.get(position));
                  intent1.putExtra("overview",biography.get(position));
                    intent1.putExtra("title",name.get(position));
                    intent1.putExtra("poster",profilePath.get(position));
                   intent1.putExtra("rating",placeOfBirth.get(position));
                    startActivity(intent1);
                }
            });


        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isNetworkAvailable()) {
          castId = new ArrayList<>();
         character = new ArrayList<>();
           name = new ArrayList<>();
            profilePath = new ArrayList<>();
          biography=new ArrayList<>();
            placeOfBirth=new ArrayList<>();
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


        Call<CastMoviesResponse> call = apiService.getCast(movieId, API_KEY);
        call.enqueue(new Callback<CastMoviesResponse>() {

            @Override
            public void onResponse(Call<CastMoviesResponse> call, Response<CastMoviesResponse> response) {
                if(response.body()!=null){
                List<CastMovie> movies = response.body().getCast();
                Log.e("Cast.java", "Number of casts received  " + movies.size());
                if (movies != null) {
                    for (int i = 0; i < movies.size(); i++) {
                        profilePath.add(movies.get(i).getProfilePath());
                        name.add(movies.get(i).getName());
                        character.add(movies.get(i).getCharacter());
                        castId.add(movies.get(i).getCastId());
                      Casts=castId.get(i);
                        Log.e("castid",Casts.toString());
                        excute2();


                    }


                    CastAdapter adapter = new CastAdapter(getActivity(), profilePath, width, name, character,sharedPref.getValue(getActivity(),"popular"));
                    if(sharedPref.getValue(getActivity(),"popular")==true)
                        castList.setNumColumns(GridView.CHOICE_MODE_SINGLE);
                    else
                        castList.setNumColumns(GridView.AUTO_FIT);
                    castList.setColumnWidth(width);
                    castList.setAdapter(adapter);

                }


            }}

            @Override
            public void onFailure(Call<CastMoviesResponse> call, Throwable t) {
                Log.e("Tag", t.toString());
            }
        });
    }
    private void excute2() {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);



            Call<PeopleMoviesResponse> call = apiService.getPeopleDetails(Casts, API_KEY);
            call.enqueue(new Callback<PeopleMoviesResponse>() {
                @Override
                public void onResponse(Call<PeopleMoviesResponse> call, Response<PeopleMoviesResponse> response) {
                    //   List<CastMovie> movies = response.body();
                    //Log.d("TAG", "Number of casts received  " + movies.size());
                    //  ArrayList<String> image = new ArrayList<String>();
                    //ArrayList<String> name = new ArrayList<String>();
                    //ArrayList<String> character = new ArrayList<String>();
                    if (response.body() != null) {

                        biography.add((response.body().getBiography()));

                        placeOfBirth.add(response.body().getPlaceOfBirth());

                    }

                }


                @Override
                public void onFailure(Call<PeopleMoviesResponse> call, Throwable t) {
                    Log.e("cast.java:", t.toString());

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
                CastAdapter adapter = new CastAdapter(getActivity(), profilePath, width, name, character,sharedPref.getValue(getActivity(),"popular"));
                castList.setAdapter(adapter);

                return ;
            case R.id.changegrid:
                castList.setNumColumns(GridView.AUTO_FIT);
                castList.setColumnWidth(width);
                sharedPref.save(getActivity(),false,"popular");
                CastAdapter adapter1 = new CastAdapter(getActivity(), profilePath, width, name, character,sharedPref.getValue(getActivity(),"popular"));
                castList.setAdapter(adapter1);
                changeGrid.setVisibility(View.GONE);
                changeList.setVisibility(View.VISIBLE);
        }

    }
}

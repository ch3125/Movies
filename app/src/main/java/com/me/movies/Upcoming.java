package com.me.movies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.me.movies.Rest.ApiClient;
import com.me.movies.Rest.ApiInterface;
import com.me.movies.model.Movie;
import com.me.movies.model.MoviesResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Upcoming extends Fragment implements View.OnClickListener {
    private static GridView gridview;

    static Integer movieid;
    static String API_KEY = "e649c1ec4f43c9f8ea307ec5aec0e891";
    static ArrayList<String> overviews;
    static ArrayList<Integer> voteCount;
    static ArrayList<String> titles;
    static ArrayList<String> dates;
    static ArrayList<String> ratings;
    static ArrayList<Integer> idintent;
    static FloatingActionButton changeList ;
    static FloatingActionButton changeGrid;
    public SharedPref sharedPref=new SharedPref();
  /*  static ArrayList<ArrayList<String>> relatedMovies=new ArrayList<>();
    static ArrayList<String> relatedPoster=new ArrayList<>();
    static ArrayList<String> backdrop=new ArrayList<>();
    static ArrayList<ArrayList<String>> backdrops=new ArrayList<>();
    static ArrayList<String> movieposter=new ArrayList<>();
    static ArrayList<ArrayList<String>> movieposters=new ArrayList<>();
    static ArrayList<Integer> relatedId=new ArrayList<>();
    static ArrayList<ArrayList<Integer>> relatedids=new ArrayList<>();*/
    //ImageAdapter adapter;


    static ArrayList<String> poster=new ArrayList<>();
    static int width;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_movies_fragment, container, false);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

       sharedPref.getValue(getActivity(),"upcoming");
        Point size = new Point();
        display.getSize(size);
        if (MainActivity.TABLET) {
            width = size.x / 6;
        } else
            width = size.x / 3;
        if (getActivity() != null) {
            ArrayList<String> array = new ArrayList<>();
            ArrayList<String> array1 = new ArrayList<>();
            ArrayList<String> array2 = new ArrayList<>();
            CastAdapter adapter = new CastAdapter(getActivity(), array, width,array1,array2,sharedPref.getValue(getActivity(),"upcoming"));
            gridview=(GridView)rootView.findViewById(R.id.gridview);
            gridview.setColumnWidth(width);
            Log.e("upcoming","oncreate adapter");
            gridview.setAdapter(adapter);
            //initializing the arraylists
            changeGrid=(FloatingActionButton) rootView.findViewById(R.id.changegrid);
            changeGrid.setOnClickListener(this);
            changeList=(FloatingActionButton) rootView.findViewById(R.id.changelist);
            changeList.setOnClickListener(this);
            if(sharedPref.getValue(getActivity(),"upcoming")==true){
                changeGrid.setVisibility(GridView.VISIBLE);
                changeList.setVisibility(GridView.INVISIBLE);
            }
            else{
                changeList.setVisibility(GridView.VISIBLE);
                changeGrid.setVisibility(GridView.INVISIBLE);

            }



            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("overview", overviews.get(position));
                    intent.putExtra("title", titles.get(position));
                    intent.putExtra("rating", ratings.get(position).concat("/10"));
                    intent.putExtra("poster", poster.get(position));
                    intent.putExtra("date", dates.get(position));
                    intent.putExtra("votecount", voteCount.get(position).toString());
                    intent.putExtra("idintent",idintent.get(position));

                    startActivity(intent);



                }
            });
        }
        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        //sharedPref.save(getActivity(),false);
        if (isNetworkAvailable()) {
            overviews = new ArrayList<>();
            titles = new ArrayList<>();
            dates = new ArrayList<>();
            ratings = new ArrayList<>();
            poster = new ArrayList<>();

            voteCount=new ArrayList<>();
            idintent=new ArrayList<>();
            excute();


        } else {
            Toast.makeText(getActivity(), "No internet connection found", Toast.LENGTH_LONG).show();
            gridview.setVisibility(GridView.GONE);
        }
    }


    private void excute() {    /////
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesResponse> call = apiService.getUpcomingMovies(API_KEY);

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if(response.body()!=null){
                    List<Movie> movies = response.body().getResults();
                    Log.e("TAG", "Number of casts received  " + movies.size());
                    if (movies != null) {
                        for (int i = 0; i < movies.size(); i++) {
                            //   strings.add(movies.get(i).getPosterPath());
                            overviews.add(movies.get(i).getOverview());
                            titles.add(movies.get(i).getTitle());
                            dates.add(movies.get(i).getReleaseDate());
                            ratings.add(movies.get(i).getVoteAverage().toString());
                            poster.add(movies.get(i).getPosterPath());
                            voteCount.add(movies.get(i).getVoteCount());
                            idintent.add(movies.get(i).getId());
                            //   movieid=idintent.get(i);
                            //    relatedMovies.add(relatedPoster);
                            //   relatedids.add(relatedId);

                        }

                        CastAdapter adapter = new CastAdapter(getActivity(), poster, width,titles,dates,sharedPref.getValue(getActivity(),"upcoming"));
                        // gridview.setNumColumns(GridView.AUTO_FIT);
                        if(sharedPref.getValue(getActivity(),"upcoming")==true)
                            gridview.setNumColumns(GridView.CHOICE_MODE_SINGLE);
                        else{
                            gridview.setNumColumns(GridView.AUTO_FIT);
                        gridview.setColumnWidth(width);}
                        Log.e("upcoming","excute adapter");
                        gridview.setAdapter(adapter);

                    }
                }}

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.changelist:
                gridview.setNumColumns(0);
                changeGrid.setVisibility(View.VISIBLE);
                changeList.setVisibility(View.GONE);
                sharedPref.save(getActivity(),true,"upcoming");
                CastAdapter adapter = new CastAdapter(getActivity(), poster, width,titles,dates,sharedPref.getValue(getActivity(),"upcoming"));
                gridview.setAdapter(adapter);

                return ;
            case R.id.changegrid:
                gridview.setNumColumns(GridView.AUTO_FIT);
                gridview.setColumnWidth(width);
                sharedPref.save(getActivity(),false,"upcoming");
                CastAdapter adapter1 = new CastAdapter(getActivity(), poster, width,titles,dates,sharedPref.getValue(getActivity(),"upcoming"));
                gridview.setAdapter(adapter1);
                changeGrid.setVisibility(View.GONE);
                changeList.setVisibility(View.VISIBLE);
        }

    }
}

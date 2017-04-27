package com.me.movies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.me.movies.Rest.ApiClient;
import com.me.movies.Rest.ApiInterface;
import com.me.movies.imagevideo.PhotoGrid;
import com.me.movies.model.CastImages;
import com.me.movies.model.CreditsMovie;
import com.me.movies.model.CreditsMoviesResponse;
import com.me.movies.model.Movie;
import com.me.movies.model.MoviesImage;
import com.me.movies.model.MoviesImageResponse;
import com.me.movies.model.MoviesResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Home on 11/22/2016.
 */

public class DetailCastFragment extends Fragment implements View.OnClickListener {
    public static String youtube;
    public static String overview;
    public static String rating;
    public static String date;
    public static String title;
    public static String poster;
    ProgressDialog progressDialog;
   // static ArrayList<String> relatedPoster;
   // ArrayList<String> backdrop;
    ArrayList<String> castposter;
    static ArrayList<String> knownfor;
   // static ArrayList<String> videokey;
    static Integer idintent;
    private ShareActionProvider mShareActionProvider;
    private GridView relatedGrid;
    private FloatingActionButton fab,fab2;
    private Animation fab_open,fab_close;
    private Boolean isFabOpen = false;
    private ImageButton titleimage;
    static String API_KEY = "e649c1ec4f43c9f8ea307ec5aec0e891";
    public DetailCastFragment() {

    }
    int width,width2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.castdetail,container,false);
        final Intent intent=getActivity().getIntent();
        getActivity().setTitle("Cast Details");
        WindowManager wm=(WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
       // ImageButton ib=(ImageButton)rootView.findViewById(R.id.poster);
        Display display=wm.getDefaultDisplay();
        Point size=new Point();
        display.getSize(size);
        width=size.x/3;
        width2=size.x;

        //initializing arraylists
       // relatedPoster=new ArrayList<>();
       // backdrop=new ArrayList<>();
        castposter=new ArrayList<>();
        knownfor=new ArrayList<>();
       // videokey=new ArrayList<>();



        //for the floating action button
        fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        //fab1 = (FloatingActionButton)rootView.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)rootView.findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.fab_close);
        fab.setOnClickListener(this);
        //fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);


        ArrayList<String> array=new ArrayList<>();

        relatedGrid=(GridView)rootView.findViewById(R.id.relatedgrid);
        relatedGrid.setColumnWidth(width);
        relatedGrid.setVisibility(GridView.VISIBLE);
        ImageAdapter adapter=new ImageAdapter(getActivity(),array,width);
        relatedGrid.setAdapter(adapter);

        if(intent!=null && intent.hasExtra("title")){
            title=intent.getStringExtra("title");
            TextView tv=(TextView)rootView.findViewById(R.id.titlename);
            tv.setText(title);

        }
        titleimage=(ImageButton)rootView.findViewById(R.id.title);
        if(intent!=null && intent.hasExtra("rating")){
            rating=intent.getStringExtra("rating");
            TextView tv=(TextView)rootView.findViewById(R.id.placebirth);
            tv.setText(rating);

        }
        if(intent!=null && intent.hasExtra("date")){
            date=intent.getStringExtra("date");
            TextView tv=(TextView)rootView.findViewById(R.id.vote);
            tv.setText(date);

        }
        if(intent!=null && intent.hasExtra("poster")){
            poster=intent.getStringExtra("poster");
            ImageButton iv=(ImageButton) rootView.findViewById(R.id.title);
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185"+poster).resize(width2,iv.getHeight()).into(iv);


        }
        if(intent!=null && intent.hasExtra("idintent")){
            idintent=intent.getIntExtra("idintent",55);
            Log.e("idintent",String.valueOf(idintent));
        }
      /*  ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),PhotoGrid.class);
                i.putExtra("movieposters",movieposter);
                Log.e("moviepostersize",String.valueOf(movieposter));
                startActivity(i);
            }
        });*/


        titleimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),PhotoGrid.class);
                i.putExtra("movieposters",castposter);
                Log.e("moviepostersize",String.valueOf(castposter));
                startActivity(i);

            }
        });
        relatedGrid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    return true;
                }
                return false;
            }
        });
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (isNetworkAvailable()) {
            if( castposter.size()==0 || knownfor.size()==0){
                excute();
                relatedGrid.setVisibility(GridView.VISIBLE);}




        } else {
            Toast.makeText(getActivity(), "No internet connection found", Toast.LENGTH_LONG).show();
            relatedGrid.setVisibility(GridView.GONE);
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

        final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        Call<CastImages> call2 = apiService.getCastImages(idintent, API_KEY);
        call2.enqueue(new Callback<CastImages>() {
            @Override
            public void onResponse(Call<CastImages> call, Response<CastImages> response) {
                if (response.body() != null) {
                    List<CastImages.castProfile> castPhoto = response.body().getProfiles();
                    if (castPhoto != null) {
                        for (int i = 0; i < castPhoto.size(); i++) {
                            castposter.add(castPhoto.get(i).getFilePath());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CastImages> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });

        //for related movies
        Call<CreditsMoviesResponse> call = apiService.getMovieCredits(idintent, API_KEY);
        call.enqueue(new Callback<CreditsMoviesResponse>() {

            @Override
            public void onResponse(Call<CreditsMoviesResponse> call, Response<CreditsMoviesResponse> response) {
                if(response.body()!=null){
                    List<CreditsMovie> movies = response.body().getCast();
                    Log.e("credits.java", "Number of movie credits received  " + movies.size());
                    //  ArrayList<String> image = new ArrayList<String>();
                    //ArrayList<String> name = new ArrayList<String>();
                    //ArrayList<String> character = new ArrayList<String>();
                    if (movies != null) {
                        if(movies.size()<6){

                        for (int i = 0; i < movies.size(); i++) {
                            knownfor.add(movies.get(i).getPosterPath());
                        }


                        }
                    else {
                            for (int i = 0; i < 6; i++) {
                                knownfor.add(movies.get(i).getPosterPath());
                            }
                        }
                        Log.e("relatedpostersize",knownfor.toString());
                    }

                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    ImageAdapter adapter=new ImageAdapter(getActivity(),knownfor,width);
                    //  Log.e("relatedmovieposters",relatedPoster.toString());
                    relatedGrid.setColumnWidth((width));
                    relatedGrid.setAdapter(adapter);

                    }


                }

            @Override
            public void onFailure(Call<CreditsMoviesResponse> call, Throwable t) {
                Log.e("failure",t.toString());
            }
        });}




    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab2:
                Intent i=new Intent(getActivity(),PhotoGrid.class);
                i.putExtra("movieposters",castposter);
                Log.e("moviepostersize",String.valueOf(castposter));
                startActivity(i);
                break;
        /*    case R.id.fab2:

                Log.d("Raj", "Fab 2");
                break;*/
        }}

    public void animateFAB(){

        if(isFabOpen){
            //fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            //fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

           // fab1.startAnimation(fab_open);
            //  fab1.setVisibility(FloatingActionButton.VISIBLE);
            //fab2.setVisibility(FloatingActionButton.VISIBLE);
            fab2.startAnimation(fab_open);
          //  fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }
}

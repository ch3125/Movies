package com.me.movies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageButton;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.me.movies.Rest.ApiClient;
import com.me.movies.Rest.ApiInterface;
import com.me.movies.imagevideo.PhotoGrid;
import com.me.movies.model.Movie;
import com.me.movies.model.MoviesImage;
import com.me.movies.model.MoviesImageResponse;
import com.me.movies.model.MoviesResponse;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.me.movies.MoviesFragment.API_KEY;

/**
 * Created by Home on 11/9/2016.
 */
public class DetailActivityFragment extends Fragment implements View.OnClickListener{
    public static String youtube;
    public static String overview;
    public static String rating;
    public static String date;
    public static String title;
    public static String poster;
    ProgressDialog progressDialog;
    static ArrayList<String> relatedPoster;
     ArrayList<String> backdrop;
     ArrayList<String> movieposter;
    static ArrayList<Integer> relatedId;
    static ArrayList<String> videokey;
    static Integer idintent;
    private GridView relatedGrid;
    private FloatingActionButton fab,fab2;

    private Animation fab_open,fab_close;
    private Boolean isFabOpen = false;
    private ImageButton titleimage;
    public DetailActivityFragment() {

    }
    int width,width2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragmentdetail,container,false);
        final Intent intent=getActivity().getIntent();
        getActivity().setTitle("Movie Details");
        WindowManager wm=(WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
        ImageButton ib=(ImageButton)rootView.findViewById(R.id.poster);
        Display display=wm.getDefaultDisplay();
        Point size=new Point();
        display.getSize(size);
        width=size.x/3;
        width2=size.x;

        //initializing arraylists
       relatedPoster=new ArrayList<>();
        backdrop=new ArrayList<>();
         movieposter=new ArrayList<>();
         relatedId=new ArrayList<>();
        videokey=new ArrayList<>();



      //for the floating action button
      fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
      //  fab1 = (FloatingActionButton)rootView.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)rootView.findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.fab_close);
        fab.setOnClickListener(this);
      //  fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);



        ArrayList<String> array=new ArrayList<>();

        relatedGrid=(GridView)rootView.findViewById(R.id.relatedgrid);
        relatedGrid.setColumnWidth(width);
        relatedGrid.setVisibility(GridView.VISIBLE);
        ImageAdapter adapter=new ImageAdapter(getActivity(),array,width);
        relatedGrid.setAdapter(adapter);

        if(intent!=null && intent.hasExtra("title")){
            title=intent.getStringExtra("title");
            TextView tv=(TextView)rootView.findViewById(R.id.date);
            tv.setText(title);

        }
        titleimage=(ImageButton)rootView.findViewById(R.id.title);
        if(intent!=null && intent.hasExtra("rating")){
            rating=intent.getStringExtra("rating");
            TextView tv=(TextView)rootView.findViewById(R.id.rating);
            tv.setText(rating);

        }
        if(intent!=null && intent.hasExtra("date")){
            date=intent.getStringExtra("date");
            SimpleDateFormat inputformat=new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputformat=new SimpleDateFormat("dd MMMM yyyy", Locale.US);
            Date datess=null;
            String str=null;
            try{
                datess=inputformat.parse(date);
                str=outputformat.format(datess);
            }catch (ParseException e){
                e.printStackTrace();
            }



            TextView tv=(TextView)rootView.findViewById(R.id.vote);
            tv.setText(str);

        }
        if(intent!=null && intent.hasExtra("poster")){
            poster=intent.getStringExtra("poster");
            ImageButton iv=(ImageButton) rootView.findViewById(R.id.poster);
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185"+poster).resize(width,(int)(width*1.5)).into(iv);


        }
        if(intent!=null && intent.hasExtra("idintent")){
            idintent=intent.getIntExtra("idintent",55);
            Log.e("idintent",String.valueOf(idintent));
        }
       ib.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(getActivity(),PhotoGrid.class);
               i.putExtra("movieposters",movieposter);
               Log.e("moviepostersize",String.valueOf(movieposter));
               startActivity(i);
           }
       });


        titleimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),PhotoGrid.class);
                i.putExtra("movieposters",backdrop);
                Log.e("moviepostersize",String.valueOf(backdrop));
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
          if(backdrop.size()==0 || movieposter.size()==0 || relatedPoster.size()==0){
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
    private void excute(){
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        Call<MoviesImageResponse> call2 = apiService.getMovieImages(idintent,API_KEY);
            call2.enqueue(new Callback<MoviesImageResponse>() {
                @Override
                public void onResponse(Call<MoviesImageResponse> call, Response<MoviesImageResponse> response) {
                    if (response.body() != null) {
                        List<MoviesImage> backdrops1 = response.body().getBackdrops();
                        List<MoviesImage> posters1 = response.body().getPosters();
                        if (backdrops1 != null) {
                            Log.e("moviesfragment",String.valueOf(backdrops1.size()));
                            for (int i = 0; i < backdrops1.size(); i++) {
                                backdrop.add(backdrops1.get(i).getFilePath());
                            }

                        Log.e("backdropsize",String.valueOf(backdrop.size()));
                            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185"+backdrop.get(0)).resize(width2,titleimage.getHeight()).into(titleimage);
                        }
                        if (posters1 != null) {
                            for (int i = 0; i < posters1.size(); i++) {
                                movieposter.add(posters1.get(i).getFilePath());
                            }
                            Log.e("titleimage",String.valueOf(movieposter.get(0)));
                           // Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185"+backdrop.get(0)).resize(width2,titleimage.getHeight()).into(titleimage);

                        }}

                }

                @Override
                public void onFailure(Call<MoviesImageResponse> call, Throwable t) {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                }
            });

        //for related movies
        Call<MoviesResponse> call3 = apiService.getSimilarMovies(idintent,API_KEY);
        call3.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if(response.body()!=null){
                    List<Movie> movies = response.body().getResults();
                    if (movies != null) {
                        if(movies.size()>6){
                            for (int i = 0; i < 6; i++) {
                                relatedPoster.add(movies.get(i).getPosterPath());
                                relatedId.add(movies.get(i).getId());
                            }
                        }
                        else{

                            for (int i = 0; i < movies.size(); i++) {
                                relatedPoster.add(movies.get(i).getPosterPath());
                                relatedId.add(movies.get(i).getId());
                            }}
                        Log.e("relatedpostersize",relatedPoster.toString());

                    }
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    ImageAdapter adapter=new ImageAdapter(getActivity(),relatedPoster,width);
                  //  Log.e("relatedmovieposters",relatedPoster.toString());
                    relatedGrid.setColumnWidth((width));
                    relatedGrid.setAdapter(adapter);
                }}


            @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.e("TAG", t.toString());
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                }
            });
    }







    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:
                animateFAB();
                break;
        /*    case R.id.fab1:


                break;*/
            case R.id.fab2:
                Intent i=new Intent(getActivity(),PhotoGrid.class);
                i.putExtra("movieposters",movieposter);
                Log.e("moviepostersize",String.valueOf(movieposter));
                animateFAB();
                startActivity(i);

                Log.d("Raj", "Fab 2");
                break;
    }}

   public void animateFAB() {

       if (isFabOpen) {
           //   fab1.startAnimation(fab_close);
           fab2.startAnimation(fab_close);
           //   fab1.setClickable(false);
           fab2.setClickable(false);
           isFabOpen = false;
           Log.d("Raj", "close");

       } else {

           // fab1.startAnimation(fab_open);

           fab2.startAnimation(fab_open);
           //   fab1.setClickable(true);
           fab2.setClickable(true);
           isFabOpen = true;
           Log.d("Raj", "open");

       }
   }}




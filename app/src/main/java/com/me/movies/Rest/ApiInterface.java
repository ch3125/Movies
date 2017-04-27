package com.me.movies.Rest;

import com.me.movies.Credits;
import com.me.movies.model.Business;
import com.me.movies.model.BusinessResponse;
import com.me.movies.model.CastImages;
import com.me.movies.model.CastMoviesResponse;
import com.me.movies.model.CreditsMoviesResponse;
import com.me.movies.model.Movie;
import com.me.movies.model.MoviesImageResponse;
import com.me.movies.model.MoviesResponse;
import com.me.movies.model.MoviesVideo;
import com.me.movies.model.PeopleMoviesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Home on 11/10/2016.
 */

public interface ApiInterface {
    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key")String apiKey);

    @GET("movie/upcoming")
    Call<MoviesResponse> getUpcomingMovies(@Query("api_key")String apiKey);
    @GET("movie/now_playing")
    Call<MoviesResponse> getNowPlayingMovies(@Query("api_key")String apiKey);

    @GET("movie/{movie_id}/similar")
    Call<MoviesResponse> getSimilarMovies(@Path("movie_id") int id,@Query("api_key") String apiKey);

    @GET("movie/{id}/credits")
    Call<CastMoviesResponse> getCast(@Path("id") int id,@Query("api_key") String apiKey);

    @GET("person/{id}")
    Call<PeopleMoviesResponse> getPeopleDetails(@Path("id") int id,@Query("api_key") String apiKey);

    @GET("person/{person_id}/movie_credits")
    Call<CreditsMoviesResponse> getMovieCredits(@Path("person_id") int id,@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/images")
    Call<MoviesImageResponse> getMovieImages(@Path("movie_id") int id,@Query("api_key")String apiKey);

   /* @GET("movie/{movie_id}/videos")
    Call<MoviesVideo> getMovieVideos(@Path("movie_id")int id,@Query("apii_key")String apiKey);*/
    @GET("person/{person_id}/images")
    Call<CastImages> getCastImages(@Path("person_id") int id, @Query("api_key")String apiKey);
    @GET("business")
    Call<List<Business>> getBusinessDetails();










}

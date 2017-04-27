package com.me.movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Home on 11/21/2016.
 */

public class MoviesImageResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("backdrops")
    List<MoviesImage> backdrops;
    @SerializedName("posters")
    List<MoviesImage> posters;

    public void setBackdrops(List<MoviesImage> backdrops) {
        this.backdrops = backdrops;
    }

    public void setPosters(List<MoviesImage> posters) {
        this.posters = posters;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<MoviesImage> getBackdrops() {
        return backdrops;
    }

    public List<MoviesImage> getPosters() {
        return posters;
    }
}

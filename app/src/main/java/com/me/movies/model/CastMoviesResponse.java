package com.me.movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Home on 11/19/2016.
 */

public class CastMoviesResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("cast")
    private List<CastMovie> cast;

    public List<CastMovie> getCast() {
        return cast;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCast(List<CastMovie> cast) {
        this.cast = cast;
    }
}

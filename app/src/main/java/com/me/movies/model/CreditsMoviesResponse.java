package com.me.movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Home on 11/20/2016.
 */

public class CreditsMoviesResponse {
    @SerializedName("cast")
   private List<CreditsMovie> cast;
    @SerializedName("crew")
    private List<CreditsMovie> crew;
    @SerializedName("id")
    private Integer id;

    public List<CreditsMovie> getCast() {
        return cast;
    }

    public List<CreditsMovie> getCrew() {
        return crew;
    }

    public Integer getId() {
        return id;
    }

    public void setCast(List<CreditsMovie> cast) {

        this.cast = cast;
    }

    public void setCrew(List<CreditsMovie> crew) {
        this.crew = crew;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

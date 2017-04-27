package com.me.movies.model;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Home on 11/20/2016.
 */

public class PeopleMoviesResponse {
    @SerializedName("id")
    private Integer Id;

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getId() {

        return Id;
    }

    @SerializedName("name")
    private String Name;
    @SerializedName("biography")
    private String Biography;
    @SerializedName("place_of_birth")
    private String placeOfBirth;

    public void setName(String name) {
        Name = name;
    }

    public void setBiography(String biography) {
        Biography = biography;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getName() {

        return Name;
    }

    public String getBiography() {
        return Biography;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }
}

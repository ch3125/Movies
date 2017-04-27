package com.me.movies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Home on 11/19/2016.
 */

public class CastMovie {
    @SerializedName("id")
    private Integer castId;
    @SerializedName("character")
    private String character;
    @SerializedName("name")
    private String name;
    @SerializedName("profile_path")
    private String profilePath;

    public CastMovie(Integer castId, String character, String name, String profilePath) {
        this.castId = castId;
        this.character = character;
        this.name = name;
        this.profilePath = profilePath;
    }

    public void setCastId(Integer castId) {
        this.castId = castId;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public Integer getCastId() {

        return castId;
    }

    public String getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }
}

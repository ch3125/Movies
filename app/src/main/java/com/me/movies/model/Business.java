package com.me.movies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Home on 11/30/2016.
 */

public class Business {
    @SerializedName("Description")
    private String Description;

    public String getDescription() {
        return Description;
    }
}

package com.me.movies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Home on 11/21/2016.
 */

public class MoviesImage {
    @SerializedName("file_path")
    private String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {

        return filePath;
    }
}

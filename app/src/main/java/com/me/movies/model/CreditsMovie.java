package com.me.movies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Home on 11/20/2016.
 */

public class CreditsMovie {
    @SerializedName("character")
    private String character;
    @SerializedName("id")
    private Integer movieid;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("title")
    private String title;

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setMovieid(Integer movieid) {
        this.movieid = movieid;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCharacter() {

        return character;
    }

    public Integer getMovieid() {
        return movieid;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public CreditsMovie(String character, Integer movieid, String originalTitle, String posterPath, String releaseDate, String title) {

        this.character = character;
        this.movieid = movieid;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
    }
}

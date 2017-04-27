package com.me.movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home on 11/23/2016.
 */

public class CastImages {

        @SerializedName("id")
        private Integer id;
        @SerializedName("profiles")
        private List<castProfile> profiles = new ArrayList<>();

        /**
         *
         * @return
         * The id
         */
        public Integer getId() {
            return id;
        }

        /**
         *
         * @param id
         * The id
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         *
         * @return
         * The profiles
         */
        public List<castProfile> getProfiles() {
            return profiles;
        }

        /**
         *
         * @param profiles
         * The profiles
         */
        public void setProfiles(List<castProfile> profiles) {
            this.profiles = profiles;
        }



    public class castProfile {

        @SerializedName("aspect_ratio")

        private Double aspectRatio;
        @SerializedName("file_path")

        private String filePath;
        @SerializedName("height")

        private Integer height;
        @SerializedName("iso_639_1")

        private Object iso6391;
        @SerializedName("vote_average")

        private Double voteAverage;
        @SerializedName("vote_count")

        private Integer voteCount;
        @SerializedName("width")
        private Integer width;

        /**
         *
         * @return
         * The aspectRatio
         */
        public Double getAspectRatio() {
            return aspectRatio;
        }

        /**
         *
         * @param aspectRatio
         * The aspect_ratio
         */
        public void setAspectRatio(Double aspectRatio) {
            this.aspectRatio = aspectRatio;
        }

        /**
         *
         * @return
         * The filePath
         */
        public String getFilePath() {
            return filePath;
        }

        /**
         *
         * @param filePath
         * The file_path
         */
        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        /**
         *
         * @return
         * The height
         */
        public Integer getHeight() {
            return height;
        }

        /**
         *
         * @param height
         * The height
         */
        public void setHeight(Integer height) {
            this.height = height;
        }

        /**
         *
         * @return
         * The iso6391
         */
        public Object getIso6391() {
            return iso6391;
        }

        /**
         *
         * @param iso6391
         * The iso_639_1
         */
        public void setIso6391(Object iso6391) {
            this.iso6391 = iso6391;
        }

        /**
         *
         * @return
         * The voteAverage
         */
        public Double getVoteAverage() {
            return voteAverage;
        }

        /**
         *
         * @param voteAverage
         * The vote_average
         */
        public void setVoteAverage(Double voteAverage) {
            this.voteAverage = voteAverage;
        }

        /**
         *
         * @return
         * The voteCount
         */
        public Integer getVoteCount() {
            return voteCount;
        }

        /**
         *
         * @param voteCount
         * The vote_count
         */
        public void setVoteCount(Integer voteCount) {
            this.voteCount = voteCount;
        }

        /**
         *
         * @return
         * The width
         */
        public Integer getWidth() {
            return width;
        }

        /**
         *
         * @param width
         * The width
         */
        public void setWidth(Integer width) {
            this.width = width;
        }

    }

}
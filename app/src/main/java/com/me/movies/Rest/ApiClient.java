package com.me.movies.Rest;

import okhttp3.internal.tls.RealTrustRootIndex;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Home on 11/10/2016.
 */

public class ApiClient {
   public static final String BASE_URL = "http://api.themoviedb.org/3/";
  // public static final String BASE_URL = "http://mlswebapi.azurewebsites.net/api/";
    private static Retrofit retrofit = null;



    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}

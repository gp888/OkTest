package com.gp.oktest;

import com.gp.oktest.model.GitModel;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by guoping on 2017/11/15.
 */

public interface GitHubService {

    String BASEURL = "https://api.github.com";

    @GET("users/{user}")
    Call<String> getData(@Path("user") String user);

    @GET("users/{user}")
    Call<GitModel> getUserInfo(@Path("user") String user);

    @GET("users/{user}")
    Call<ResponseBody> getResponseBody(@Path("user") String user);

    @GET("users/{user}")
    Observable<GitModel> rxUser(@Path("user") String user);
}

package com.aditya.news.api;

import com.aditya.news.models.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<NewsModel> getNews(
            @Query("country") String country ,
            @Query("apiKey") String apiKey

    );
    @GET("top-headlines")
    Call<NewsModel> getNewsbycategory(
            @Query("country") String country ,
            @Query("category") String category,
            @Query("apiKey") String apiKey

    );

    @GET("everything")
    Call<NewsModel> getNewsbySearch(
            @Query("q") String keyword,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey

    );
    @GET("everything")
    Call<NewsModel> getNewsbydomain(
            @Query("domains") String domain,
            @Query("apiKey") String apiKey

    );

}

package com.aditya.news.Config;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.aditya.news.api.ApiClient;
import com.aditya.news.api.ApiInterface;
import com.aditya.news.models.NewsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class apiconfig {
    final public static String API_KEY_1 = "1a6fb5e756684298b67fbd7e9d8ffd77";
    final public static String API_KEY_2 = "4ca6efcfa26a4eb4bf883068e1058ddb";
    public static String API_KEY = null;
    static Context contexts;
    static ApiInterface apiInterface;

    public static String apikeycheck(Context context) {
        contexts = context;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        switch (API_KEY_1) {
            case API_KEY_1:
                if (requestserver(API_KEY_1) != null) {
                    API_KEY = API_KEY_1;
                }
            case API_KEY_2:
                if (requestserver(API_KEY_2) != null) {
                    API_KEY = API_KEY_2;
                }
        }
        return API_KEY;
    }

    static Boolean requestserver(String apikey) {
        Call<NewsModel> callnews = apiInterface.getNews("IN", apikey);
        callnews.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                try {

                    if (response.isSuccessful()) {
                        Log.i("result", "successful");
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(contexts, "No internet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                try {
                    Toast.makeText(contexts.getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }
}

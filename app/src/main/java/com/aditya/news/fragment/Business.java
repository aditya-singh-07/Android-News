package com.aditya.news.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aditya.news.R;
import com.aditya.news.adapter.NewsLatestAdapter;
import com.aditya.news.api.ApiClient;
import com.aditya.news.api.ApiInterface;
import com.aditya.news.connection.Checknetwork;
import com.aditya.news.models.Article;
import com.aditya.news.models.NewsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aditya.news.Config.apiconfig.apikeycheck;
import static com.aditya.news.api.ApiClient.init;
import static com.aditya.news.fragment.Home.getCountry;
import static com.aditya.news.fragment.Home.getLanguage;

public class Business extends Fragment {
    FrameLayout frameLayout;
    RecyclerView recyclerView;
    List<Article> articles;
    NewsLatestAdapter adapter;
    ApiInterface apiInterface;
    TextView topnews;
    SwipeRefreshLayout swipeRefreshLayout;
    Context context;
    Checknetwork checknetwork;
    Boolean ntstatus = false;

    public Business() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        frameLayout = getActivity().findViewById(R.id.framelayout);
        if (Checknetwork.getConnectivityStatusString(getContext())) {
            ntstatus = true;
        } else {
            ntstatus = false;
        }
        init(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewbusiness);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_business);
        swipeRefreshLayout.setColorSchemeResources(R.color.purple_200,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        try {
            swipeRefreshLayout.post(new Runnable() {

                @Override
                public void run() {

                    swipeRefreshLayout.setRefreshing(true);

                    // Fetching data from server
                    oninit();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    oninit();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;

    }

    private void oninit() {
        String category = "Business";
        String country = getCountry();
        String language = getLanguage();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        if (ntstatus) {
            Call<NewsModel> callnews = apiInterface.getNewsbycategory(country, category, apikeycheck(getContext()));
            callnews.enqueue(new Callback<NewsModel>() {
                @Override
                public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                    articles = new ArrayList<>();
                    try {

                        if (response.isSuccessful()) {
                            articles = response.body().getArticles();
                            swipeRefreshLayout.setRefreshing(false);
                            Log.i("result", articles.toString());
                            adapter = new NewsLatestAdapter(getContext(), articles);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            swipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(context, "No internet plzz try again", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "No internet", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<NewsModel> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    try {
                        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Error()).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
        }


    }
}
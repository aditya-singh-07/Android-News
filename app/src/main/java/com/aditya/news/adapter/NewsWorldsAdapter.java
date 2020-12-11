package com.aditya.news.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aditya.news.NewsDetail;
import com.aditya.news.R;
import com.aditya.news.models.Article;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class NewsWorldsAdapter extends RecyclerView.Adapter<NewsWorldsAdapter.Worldviewholder> {
    Context context;
    private List<Article> articles;

    public NewsWorldsAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public NewsWorldsAdapter.Worldviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.latest, parent, false);
        return new NewsWorldsAdapter.Worldviewholder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull NewsWorldsAdapter.Worldviewholder holder, int position) {
        Article model = articles.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(model.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageView);

        holder.latest_source.setText(model.getSource().getName());
        holder.title.setText(model.getTitle());
        holder.time.setText(NewsDetail.DateToTimeFormat(model.getPublishedAt()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, NewsDetail.class);
                i.putExtra("title", model.getSource().getName());
                i.putExtra("headline", model.getTitle());
                i.putExtra("date", NewsDetail.DateFormat(model.getPublishedAt()));
                i.putExtra("time", NewsDetail.DateToTimeFormat(model.getPublishedAt()));
                i.putExtra("image", model.getUrlToImage());
                i.putExtra("url", model.getUrl());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class Worldviewholder extends RecyclerView.ViewHolder {
        TextView latest_source, title, description, author, published_at, time;
        ImageView imageView;
        ProgressBar progressBar;
        CardView cardView;

        public Worldviewholder(@NonNull View itemView) {
            super(itemView);
            latest_source = itemView.findViewById(R.id.latest_source);
            cardView = itemView.findViewById(R.id.cardviewlatest);
            title = itemView.findViewById(R.id.latest_title);
            progressBar = itemView.findViewById(R.id.progressbar_latest);
            imageView = itemView.findViewById(R.id.imageViewlatest);
            time = itemView.findViewById(R.id.time_latest);

        }
    }
}

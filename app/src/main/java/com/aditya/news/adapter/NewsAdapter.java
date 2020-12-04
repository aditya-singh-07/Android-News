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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private List<Article> articles;
    private Context context;


    public NewsAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_items, parent, false);
        return new NewsHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
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

        holder.title.setText(model.getTitle());
//        holder.description.setText(model.getDescription());
        holder.source.setText(model.getSource().getName());
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//        Date date = null;
//        try {
//            date = formatter.parse(model.getPublishedAt());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Date is: "+date);
        holder.time.setText(NewsDetail.DateToTimeFormat(model.getPublishedAt()));
        holder.published_at.setText(NewsDetail.DateFormat(model.getPublishedAt()));
        holder.author.setText(model.getAuthor());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, NewsDetail.class);
                i.putExtra("title",model.getSource().getName());
                i.putExtra("headline",model.getTitle());
                i.putExtra("date",NewsDetail.DateFormat(model.getPublishedAt()));
                i.putExtra("time",NewsDetail.DateToTimeFormat(model.getPublishedAt()));
                i.putExtra("image",model.getUrlToImage());
                i.putExtra("url",model.getUrl());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
        TextView title, description, author, published_at, source, time;
        ImageView imageView;
        ProgressBar progressBar;
        CardView cardView;
        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardview);
            title = itemView.findViewById(R.id.title);
//            description = itemView.findViewById(R.id.description);
            author = itemView.findViewById(R.id.author);
            published_at = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.image);
            progressBar = itemView.findViewById(R.id.progress);
        }
    }
}

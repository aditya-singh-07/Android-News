package com.aditya.news.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.aditya.news.NewsDetail;
import com.aditya.news.R;
import com.aditya.news.models.notificationmodel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<notificationmodel> articles;
    private Context context;


    public NotificationAdapter(List<notificationmodel> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification, parent, false);
        return new NotificationAdapter.NotificationViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        notificationmodel model = articles.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
        Glide.with(context)
                .load(model.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.imageView);

        holder.title.setText(model.getTitle());
//        holder.description.setText(model.getDescription());
        holder.source.setText(model.getSource());
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//        Date date = null;
//        try {
//            date = formatter.parse(model.getPublishedAt());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Date is: "+date);
        holder.time.setText(model.getPublishedAt());
//        holder.published_at.setText(NewsDetail.DateFormat(model.getPublishedAt()));
//        holder.author.setText(model.getAuthor());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "position->" + model.getPosition(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, NewsDetail.class);
                i.putExtra("title", model.getSource());
                i.putExtra("headline", model.getTitle());
                i.putExtra("date", model.getPublishedAt());
                i.putExtra("time", model.getPublishedAt());
                i.putExtra("image", model.getUrlToImage());
                i.putExtra("url", model.getUrl());
                context.startActivity(i);
            }
        });
        holder.btnbottomsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
                View bottomsheet=LayoutInflater.from((FragmentActivity) context).inflate(R.layout.bottomsheet_newsstand,null);
                bottomsheet.findViewById(R.id.constraintLayoutsave).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        File folder = new File(context.getFilesDir().getAbsolutePath() + "/" +  model.getPosition());
                        folder.delete();
                        articles.remove(position);
                        notifyDataSetChanged();

                        Toast.makeText(context, "delete success", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomsheet);
                bottomSheetDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView title, author, published_at, source, time;
        ImageView imageView;
        ProgressBar progressBar;
        CardView cardView;
        ImageButton btnbottomsheet;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.notificationcardview);
            title = itemView.findViewById(R.id.notificationtitle);
//            description = itemView.findViewById(R.id.description);
//            author = itemView.findViewById(R.id.author);
//            published_at = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.notificationsource);
            time = itemView.findViewById(R.id.notificationtime);
            imageView = itemView.findViewById(R.id.notificationimageview);
            btnbottomsheet=itemView.findViewById(R.id.btnbottomsheet);
        }
    }
}

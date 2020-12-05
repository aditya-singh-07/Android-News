package com.aditya.news.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.aditya.news.models.Article;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.security.auth.Subject;

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
//        holder.published_at.setText(NewsDetail.DateFormat(model.getPublishedAt()));
//        holder.author.setText(model.getAuthor());
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

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context,R.style.AppBottomSheetDialogTheme);
                View bottomsheet=LayoutInflater.from((FragmentActivity)context).inflate(R.layout.bottomsheet,null);
                bottomsheet.findViewById(R.id.constraintLayoutsave).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("title", model.getSource().getName());
                            jsonObject.put("headline",model.getTitle());
                            jsonObject.put("time", NewsDetail.DateToTimeFormat(model.getPublishedAt()));
                            jsonObject.put("image", model.getUrlToImage());
                            jsonObject.put("url", model.getUrl());
                            String userString = jsonObject.toString();
                            String filename="tempdata";
                            File file = new File(context.getFilesDir().getAbsolutePath(),filename);
                            FileWriter fileWriter = new FileWriter(file);
                            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                            bufferedWriter.write(userString);
                            bufferedWriter.close();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(context, "saved sucessfully", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomsheet.findViewById(R.id.constraintLayoutshare).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uri=model.getUrl();
                        Intent intent=new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT,model.getSource().getName() + ':' + '\n');
                        intent.putExtra(Intent.EXTRA_TEXT, model.getTitle()+ '\n' + uri);
                        context.startActivity(Intent.createChooser(intent,uri));
                        Toast.makeText(context, "send sucessfully", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomsheet.findViewById(R.id.constraintlayoutviewonweb).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(model.getUrl()));
                        context.startActivity(intent);
                        Toast.makeText(context, "View on web sucessfully", Toast.LENGTH_SHORT).show();
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

    public class NewsHolder extends RecyclerView.ViewHolder {
        TextView title, description, author, published_at, source, time;
        ImageView imageView;
        ProgressBar progressBar;
        CardView cardView;
        ImageButton imageButton;
        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardview);
            title = itemView.findViewById(R.id.title);
            imageButton=itemView.findViewById(R.id.btnbottomsheet);
//            description = itemView.findViewById(R.id.description);
//            author = itemView.findViewById(R.id.author);
//            published_at = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.image);
            progressBar = itemView.findViewById(R.id.progress);
        }
    }
}

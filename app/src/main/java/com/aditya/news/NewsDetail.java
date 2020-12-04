package com.aditya.news;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.AppBarLayout;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewsDetail extends AppCompatActivity{
    private ImageView imageView;
    private TextView appbartitle, appbar_date, time, title;
    private boolean isHideToolbarView = false;
    private FrameLayout framelayoutdate;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    ProgressBar progressBar;
    private String news_url, news_img, news_title, news_date, source, s_author;
    WebView webView;
   ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        toolbar = findViewById(R.id.toolbar);
//        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
//        collapsingToolbarLayout.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        framelayoutdate = findViewById(R.id.date_publish);
//        titleAppbar = findViewById(R.id.title_appbar);
//        imageView = findViewById(R.id.backgroundimage);
//        appbartitle = findViewById(R.id.title_on_appbar);
//        appbar_date = findViewById(R.id.date);
//        time = findViewById(R.id.time);
//        title = findViewById(R.id.title);
        progressBar=findViewById(R.id.progress_bar);
//       pd = ProgressDialog.show(this, "", "Loading...",true);
        String newstitle=getIntent().getStringExtra("title");
        String newsheadline=getIntent().getStringExtra("headline");
        String newsdate=getIntent().getStringExtra("date");
        String newstime=getIntent().getStringExtra("time");
        String url=getIntent().getStringExtra("url");
        getSupportActionBar().setTitle(newstitle);
//        title.setText(newsheadline);
//        time.setText(newstime);
//        appbar_date.setText(newsdate);
//        appBarLayout = findViewById(R.id.appbar);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
//                    appbartitle.setText(newstitle);
//                    framelayoutdate.setVisibility(View.INVISIBLE);
//
//                } else if (verticalOffset == 0) {
//                    appbartitle.setText("");
//                    framelayoutdate.setVisibility(View.VISIBLE);
//
//
//                } else {
//                    // Somewhere in between
//                }
//            }
//        });
//        loadGlide();

        WebView(url);

    }

    //    @SuppressLint("CheckResult")
//    private void loadGlide() {
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
//        requestOptions.centerCrop();
//
//        Glide.with(getApplicationContext())
//                .load(getIntent().getStringExtra("image"))
//                .apply(requestOptions)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        progressBar.setVisibility(View.GONE);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                       progressBar.setVisibility(View.GONE);
//                        return false;
//                    }
//                })
//                .into(imageView);
//
//    }
    private void WebView(String url){
        webView = findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setDefaultFontSize(16);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebViewClient( new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if(progressBar!=null && progressBar.isAnimating())
                {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

//            @Override
//            public void onPageCommitVisible(WebView view, String url) {
//                super.onPageCommitVisible(view, url);
//                if(pd!=null && pd.isShowing())
//                {
//                    pd.dismiss();
//                }
//            }
        });
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                if(pd!=null && pd.isShowing())
//                {
//                    pd.dismiss();
//                }
//            }
//        });
        webView.loadUrl(url);

    }


    public static String DateToTimeFormat(String stringdatetime){
        PrettyTime pretty = new PrettyTime(new Locale("DEFAULT"));
        String time = null;
        try {
            SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
                    Locale.getDefault());
            Date date = simpledate.parse(stringdatetime);
            time = pretty.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }

    public static String DateFormat(String oldstringDate){
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy", new Locale("DEFAULT"));
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldstringDate);
            newDate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldstringDate;
        }

        return newDate;
    }
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
           webView.goBack();
        }else{
            super.onBackPressed();
            supportFinishAfterTransition();
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
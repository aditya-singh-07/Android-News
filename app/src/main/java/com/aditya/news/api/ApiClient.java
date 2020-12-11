package com.aditya.news.api;

import android.app.Activity;
import android.content.Context;

import com.aditya.news.connection.Checknetwork;

import java.io.File;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class ApiClient {
    public static final String BASE_URl_localhost = "https://newsapi.org/v2/";
    public static final String apikey = "4ca6efcfa26a4eb4bf883068e1058ddb";
    //    public static final String BASE_URl_infinitywebhost ="http://travelas.infinityfreeapp.com/TravelAs/users/";
    public static Retrofit retrofit = null;
    static Context context;
    static Cache cache;

    public static void init(Activity activity) {
        context = activity;
        File cacheFile = new File(activity.getApplicationContext().getCacheDir().getAbsolutePath(), "HttpCache");
        int cacheSize = 10 * 1024 * 1024;
        cache = new Cache(cacheFile, cacheSize);
    }

    public static Retrofit getApiClient() {
        Timber.plant(new Timber.DebugTree());
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Timber.i(message));
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


//        OkHttpClient.Builder okhttp= new OkHttpClient.Builder();
//        okhttp.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//            Request request= chain.request();
//            Request.Builder newrequest=request.newBuilder().header("auth","secret key");
//           return chain.proceed( newrequest.build());
//            }
//        });
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URl_localhost).client(getUnsafeOkHttpClient().cache(cache).addInterceptor(httpLoggingInterceptor)
                    .addNetworkInterceptor(chain -> {
                                Request request = chain.request();
                                if (Checknetwork.getConnectivityStatusString(context)) {
//                             request.newBuilder().cacheControl(new CacheControl.Builder().onlyIfCached()
//                                     .maxAge(0, TimeUnit.SECONDS)
//                                     .build()).build();
                                    request.newBuilder().header("Cache-control", "public, max-age=" + 60 * 60 * 24 * 28).build();
                                } else {
                                    request.newBuilder().header("Cache-control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 28).build();
                                }
                                return chain.proceed(request);
                            }
                    )
                    .build()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;

    }

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);

            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

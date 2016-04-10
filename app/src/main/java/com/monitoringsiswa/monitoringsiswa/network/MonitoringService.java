package com.monitoringsiswa.monitoringsiswa.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import retrofit.BaseUrl;
import retrofit.GsonConverterFactory;
import retrofit.HttpException;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by root on 09/04/16.
 */
public class MonitoringService {
    private interface KilapApi {

    }

    private KilapApi kilapApi;

    private Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public MonitoringService(Context context){
        // 1. Prepare the baseUrl. We will dynamically resolve this from the SharedPreferences.
        BaseUrl baseUrl = new BaseUrl() {
            @Override
            public HttpUrl url() {
                final String baseUrl = "http://192.168.1.100:8000/";
                return HttpUrl.parse(baseUrl);
            }
        };

        // 2. Create and prepare the Client that will be the "backend".
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(120, TimeUnit.SECONDS);
        client.setReadTimeout(120, TimeUnit.SECONDS);

        // 3. Create and prepare the logging interceptor. This is mainly for debugging purpose.
        // TBD: Is it better to use Stetho instead?
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("retrofit", message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(loggingInterceptor);

        // 4. Almost done. Now, we can create the Retrofit instance.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        // 5. Finally, we can create the model of the API.
        kilapApi = retrofit.create(KilapApi.class);


    }






}

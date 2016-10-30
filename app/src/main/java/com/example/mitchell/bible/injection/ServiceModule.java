package com.example.mitchell.bible.injection;

import android.support.annotation.NonNull;

import com.example.mitchell.bible.service.AuthenticationInterceptor;
import com.example.mitchell.bible.service.VersionService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by mitchell on 10/29/16.
 */

@Module
public class ServiceModule {

    private static final String API_BASE_URL = "https://api.biblia.com";

    @Provides
    public VersionService provideMarvelComicsService() {
        return buildRetrofit().create(VersionService.class);
    }

    @NonNull
    private Retrofit buildRetrofit() {
        final RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addNetworkInterceptor(new AuthenticationInterceptor());

        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addNetworkInterceptor(httpLoggingInterceptor);

        final Gson gson = new GsonBuilder().setLenient().create();

        return new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .client(builder.build())
                .build();
    }

    @Provides
    public MainThreadSchedulerProvider providesMainThreadSchedulerProvider() {
        return new MainThreadSchedulerProvider();
    }

    @Provides
    public IoThreadSchedulerProvider providesIoThreadSchedulerProvider() {
        return new IoThreadSchedulerProvider();
    }
}
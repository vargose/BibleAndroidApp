package com.example.mitchell.bible.service;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mitchell on 10/11/16.
 */

public class AuthenticationInterceptor implements Interceptor {
    static final String PUBLIC_API_KEY = "056815ed4d1083cb6015ee47fc7ca49d";

    @Override
    public Response intercept(final Interceptor.Chain chain) throws IOException {
        final Request original = chain.request();
        final HttpUrl originalHttpUrl = original.url();

        final String timeStamp = String.valueOf(System.currentTimeMillis());

        final HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("key", PUBLIC_API_KEY)
                .build();

        final Request.Builder requestBuilder = original.newBuilder().url(url);

        final Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}

package com.example.mitchell.bible.service;


import com.example.mitchell.bible.service.model.ChapterResponse;
import com.example.mitchell.bible.service.model.TableOfContentsResponse;
import com.example.mitchell.bible.service.model.VersionResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface VersionService {
    @GET("/v1/bible/find")
    Observable<VersionResponse> versions(@Query("query") String versionName);

    //https://api.biblia.com/v1/bible/content/LEB.json?passage=John1&outputFormat=json&key=fd37d8f28e95d3be8cb4fbc37e15e18e
    @GET("/v1/bible/content/{bible}.json")
    Observable<ChapterResponse> chapter(@Path("bible") String bible, @Query("passage") String chapterName);

    //https://api.biblia.com/v1/bible/contents/LEB
    @GET("v1/bible/contents/{bible}")
    Observable<TableOfContentsResponse> contents(@Path("bible") String bible);
}
package com.example.mitchell.bible.injection;

import android.content.Context;

import com.example.mitchell.bible.context.BibleApp;
import com.example.mitchell.bible.data.ObservableDatabase;
import com.example.mitchell.bible.feature.MainPresenter;
import com.example.mitchell.bible.feature.VersionListPresenter;
import com.example.mitchell.bible.service.VersionService;
import com.example.mitchell.bible.view.GlideImageLoaderImpl;
import com.example.mitchell.bible.view.ImageLoader;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mitchell on 9/27/16.
 */

@Module
public class ApplicationModule {
    private BibleApp bibleApp;

    public ApplicationModule(BibleApp bibleApp) {
        this.bibleApp = bibleApp;
    }

    @Provides
    @Singleton
    public BibleApp provideApplication() {
        return bibleApp;
    }

    @Provides
    @Singleton
    public ObservableDatabase provideObservableDatabase() {
        return new ObservableDatabase(bibleApp.getApplicationContext());
    }

    @Provides
    @Singleton
    @Named("application_context")
    public Context provideApplicationContext() {
        return bibleApp.getApplicationContext();
    }

    @Provides
    @Singleton
    public ImageLoader provideImageLoader() {
        return new GlideImageLoaderImpl(bibleApp);
    }

    @Provides
    public VersionListPresenter provideComicListPresenter(VersionService versionService, IoThreadSchedulerProvider ioThreadSchedulerProvider, MainThreadSchedulerProvider mainThreadSchedulerProvider){
        return new VersionListPresenter(versionService, ioThreadSchedulerProvider, mainThreadSchedulerProvider);
    }

    @Provides
    public MainPresenter provideMainPresenter(VersionService versionService, IoThreadSchedulerProvider ioThreadSchedulerProvider, MainThreadSchedulerProvider mainThreadSchedulerProvider){
        return new MainPresenter(versionService, ioThreadSchedulerProvider, mainThreadSchedulerProvider);
    }

}
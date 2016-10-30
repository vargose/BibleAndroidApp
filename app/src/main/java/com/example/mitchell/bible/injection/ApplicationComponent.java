package com.example.mitchell.bible.injection;

import com.example.mitchell.bible.feature.ChapterFragment;
import com.example.mitchell.bible.feature.MainActivity;
import com.example.mitchell.bible.feature.VersionListActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mitchell on 9/27/16.
 */

@Singleton
@Component(modules = {ApplicationModule.class, ServiceModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
    void inject(ChapterFragment fragment);
    void inject(VersionListActivity versionListActivity);
}

package com.example.mitchell.bible.context;

import android.app.Application;

import com.example.mitchell.bible.injection.DaggerApplicationComponent;
import com.example.mitchell.bible.injection.ApplicationComponent;
import com.example.mitchell.bible.injection.ApplicationModule;

/**
 * Created by mitchell on 9/27/16.
 */

public class BibleApp extends BaseApplication {

    private ApplicationComponent component;

    @Override
    public ApplicationComponent getComponent() {
        if (component == null) {
            component = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return component;
    }
}

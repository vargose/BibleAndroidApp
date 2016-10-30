package com.example.mitchell.bible.context;

import android.app.Application;

import com.example.mitchell.bible.injection.ApplicationComponent;

/**
 * Created by mitchell on 10/25/16.
 */

public abstract class BaseApplication extends Application {

    public abstract ApplicationComponent getComponent();
}
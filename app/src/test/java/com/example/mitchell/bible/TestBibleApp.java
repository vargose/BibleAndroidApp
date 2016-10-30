package com.example.mitchell.bible;

import com.example.mitchell.bible.context.BibleApp;
import com.example.mitchell.bible.injection.ApplicationComponent;

/**
 * Created by mitchell on 10/4/16.
 */

public class TestBibleApp extends BibleApp {
    private ApplicationComponent testApplicationComponent;

    @Override
    public ApplicationComponent getComponent() {
        return testApplicationComponent;
    }

    public void setComponent(ApplicationComponent testApplicationComponent) {
        this.testApplicationComponent = testApplicationComponent;
    }
}
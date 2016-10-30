package com.example.mitchell.bible.injection;


import android.content.Context;

import com.example.mitchell.bible.context.BaseApplication;


public final class InjectionHelper {

    private InjectionHelper() {
    }

    public static ApplicationComponent getApplicationComponent(final Context context) {
        return ((BaseApplication) context.getApplicationContext()).getComponent();
    }

}

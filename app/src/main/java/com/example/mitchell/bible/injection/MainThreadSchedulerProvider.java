package com.example.mitchell.bible.injection;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by mitchell on 10/11/16.
 */

public class MainThreadSchedulerProvider {
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}

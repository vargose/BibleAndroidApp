package com.example.mitchell.bible.injection;

/**
 * Created by mitchell on 10/11/16.
 */

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class IoThreadSchedulerProvider {
    public Scheduler getScheduler() {
        return Schedulers.io();
    }
}
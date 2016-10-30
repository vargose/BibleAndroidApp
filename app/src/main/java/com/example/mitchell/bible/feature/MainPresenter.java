package com.example.mitchell.bible.feature;

import android.util.Log;

import com.example.mitchell.bible.service.VersionService;
import com.example.mitchell.bible.injection.IoThreadSchedulerProvider;
import com.example.mitchell.bible.injection.MainThreadSchedulerProvider;
import com.example.mitchell.bible.service.data.TableOfContentsResponse;
import com.example.mitchell.bible.view.Presenter;

import javax.inject.Inject;

/**
 * Created by mitchell on 10/11/16.
 */

public class MainPresenter extends Presenter<MainActivity> {
    private final VersionService versionService;
    private final IoThreadSchedulerProvider subscribeOn;
    private final MainThreadSchedulerProvider observeOn;

    @Inject
    public MainPresenter(final VersionService marvelComicsService, final IoThreadSchedulerProvider subscribeOn, final MainThreadSchedulerProvider observeOn) {
        this.versionService = marvelComicsService;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    void refreshChapters(String bible) {
        versionService.contents(bible).subscribeOn(subscribeOn.getScheduler()).observeOn(observeOn.getScheduler()).subscribe(this::handleResponse, this::handleError);
    }

    private void handleResponse(final TableOfContentsResponse tableOfContentsResponse) {
        if (!isViewAttached()) {
            return;
        }

        getPresenterView().displayChapters(tableOfContentsResponse.getBooks().get(0).getChapters(), tableOfContentsResponse.getBooks());
    }

    private void handleError(final Throwable throwable) {
        Log.e(getClass().getSimpleName(), throwable.getLocalizedMessage(), throwable);
        if (!isViewAttached()) {
            return;
        }
        getPresenterView().handleError(throwable);
    }
}
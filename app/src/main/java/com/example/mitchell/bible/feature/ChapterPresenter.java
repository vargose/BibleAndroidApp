package com.example.mitchell.bible.feature;

import android.util.Log;

import com.example.mitchell.bible.service.VersionService;
import com.example.mitchell.bible.injection.IoThreadSchedulerProvider;
import com.example.mitchell.bible.injection.MainThreadSchedulerProvider;
import com.example.mitchell.bible.service.model.ChapterResponse;
import com.example.mitchell.bible.view.Presenter;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by mitchell on 10/27/16.
 */

public class ChapterPresenter extends Presenter<ChapterFragment> {

    private final VersionService versionService;
    private final IoThreadSchedulerProvider subscribeOn;
    private final MainThreadSchedulerProvider observeOn;
    private String chapterName;
    private String bibleName;


    @Inject
    public ChapterPresenter(final VersionService versionService, final IoThreadSchedulerProvider subscribeOn, final MainThreadSchedulerProvider observeOn) {
        this.versionService = versionService;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    void setChapterName(final String chapterName) {
        this.chapterName = chapterName;
    }

    void setBibleName(final String bibleName) {
        this.bibleName = bibleName;
    }



    void refreshChapterData() {
        if (chapterName == null || bibleName == null) {
            return;
        }

        versionService.chapter(bibleName,chapterName)
                .subscribeOn(subscribeOn.getScheduler())
                .observeOn(observeOn.getScheduler())
                .subscribe(this::handleResponse, this::handleError);
    }

    private void handleResponse(final ChapterResponse chapterResponse) {
        if (!isViewAttached()) {
            return;
        }

        getPresenterView().updateName(chapterName);
        getPresenterView().updateChapterText(chapterResponse.getChapterText());
    }

    private void handleError(final Throwable throwable) {
        Log.e(getClass().getSimpleName(), throwable.getLocalizedMessage(), throwable);

        if (!isViewAttached()) {
            return;
        }

        getPresenterView().handleError(throwable);
    }
}

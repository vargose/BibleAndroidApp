package com.example.mitchell.bible.feature;

import android.content.ContentValues;
import android.util.Log;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.example.mitchell.bible.data.ObservableDatabase;
import com.example.mitchell.bible.data.entities.Chapter;
import com.example.mitchell.bible.service.VersionService;
import com.example.mitchell.bible.injection.IoThreadSchedulerProvider;
import com.example.mitchell.bible.injection.MainThreadSchedulerProvider;
import com.example.mitchell.bible.service.model.ChapterResponse;
import com.example.mitchell.bible.view.Presenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mitchell on 10/27/16.
 */

public class ChapterPresenter extends Presenter<ChapterFragment> {

    private final VersionService versionService;
    private final IoThreadSchedulerProvider subscribeOn;
    private final MainThreadSchedulerProvider observeOn;
    private ObservableDatabase observableDatabase;
    private CompositeSubscription subscription;
    private String chapterName;
    private String bibleName;

    private Chapter chapter;

    @Inject
    public ChapterPresenter(final VersionService versionService, final IoThreadSchedulerProvider subscribeOn, final MainThreadSchedulerProvider observeOn, final ObservableDatabase observableDatabase) {
        this.versionService = versionService;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
        this.observableDatabase = observableDatabase;
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

        if (subscription != null) {
            subscription.unsubscribe();
        }

        subscription = new CompositeSubscription();
        Chapter defaultValue = new Chapter(chapterName);
        defaultValue.setFavorite(false);
        subscription.add(observableDatabase.observeChapter(chapterName)
                .observeOn(AndroidSchedulers.mainThread())
                .defaultIfEmpty(defaultValue)
                .subscribe(this::updateFavorite));
    }

    private void updateFavorite(final Chapter chapter) {
        this.chapter = chapter;
        if(getPresenterView() !=null && chapter != null){
            getPresenterView().updateFavoriteImage(chapter.isFavorite());
        }
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

    public void toggleFavorite() {
        boolean favorite = chapter != null ? !chapter.isFavorite() : true;
        final ContentValues values = new Chapter.Builder().chapterName(chapterName).favorite(favorite).build();
        observableDatabase.updateChapter(chapterName, values);
    }
}

package com.example.mitchell.bible.feature;

import com.example.mitchell.bible.injection.IoThreadSchedulerProvider;
import com.example.mitchell.bible.injection.MainThreadSchedulerProvider;
import com.example.mitchell.bible.service.VersionService;
import com.example.mitchell.bible.service.data.VersionResponse;
import com.example.mitchell.bible.view.Presenter;

/**
 * Created by mitchell on 10/29/16.
 */

public class VersionListPresenter extends Presenter<VersionListActivity> {

    private final VersionService versionService;
    private final IoThreadSchedulerProvider subscribeOn;
    private final MainThreadSchedulerProvider observeOn;

    public VersionListPresenter(final VersionService versionService, final IoThreadSchedulerProvider subscribeOn, final MainThreadSchedulerProvider observeOn) {
        this.versionService = versionService;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    void refreshChapters() {
        versionService.versions("")
                .subscribeOn(subscribeOn.getScheduler())
                .observeOn(observeOn.getScheduler())
                .subscribe(this::handleResponse, this::handleError);
    }

    private void handleError(Throwable throwable) {
        getPresenterView().showError();

    }

    private void handleResponse(VersionResponse versionResponse) {
        getPresenterView().showVersions(versionResponse.getBibles());

    }

    public void versionClicked(String versionId) {
        getPresenterView().startVersionCardsActivity(versionId);
    }
}

package com.example.mitchell.bible.view;

/**
 * Created by mitchell on 10/11/16.
 */

public class Presenter<T extends PresenterView> {
    private T presenterView;

    public final void attachView(final T presenterView) {
        this.presenterView = presenterView;
    }

    public final void detachView() {
        presenterView = null;
    }

    public boolean isViewAttached() {
        return presenterView != null;
    }

    public T getPresenterView() {
        return presenterView;
    }
}
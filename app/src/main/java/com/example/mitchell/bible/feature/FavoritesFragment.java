package com.example.mitchell.bible.feature;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.mitchell.bible.view.PresenterView;

/**
 * Created by mitchell on 11/15/16.
 */

public class FavoritesFragment extends Fragment implements PresenterView {

    public static Fragment newInstance(String bible, String chapter) {
        final Bundle arguments = new Bundle();
       
        final FavoritesFragment fragment = new FavoritesFragment();

        return fragment;
    }
}

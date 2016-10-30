package com.example.mitchell.bible.feature;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mitchell.bible.R;
import com.example.mitchell.bible.injection.InjectionHelper;
import com.example.mitchell.bible.view.PresenterView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mitchell on 10/27/16.
 */

public class ChapterFragment extends Fragment implements PresenterView {

    private static final String CHAPTER_NAME = "CHAPTER_NAME";
    private static final String BIBLE_NAME = "BIBLE_NAME";
    private static final String BOOKS_NAME = "BOOKS_NAME";


    @BindView(R.id.chapter_name)
    TextView nameView;
    @BindView(R.id.chapter_text)
    TextView chapterTextView;
    @BindView(R.id.book_spinner)
    Spinner bookSpinner;

    @Inject
    ChapterPresenter chapterPresenter;

    public static Fragment newInstance(String bible, String chapter, ArrayList<String> books) {
        final Bundle arguments = new Bundle();
        arguments.putString(CHAPTER_NAME, chapter);
        arguments.putString(BIBLE_NAME, bible);
        arguments.putStringArrayList(BOOKS_NAME, books);
        final ChapterFragment fragment = new ChapterFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        InjectionHelper.getApplicationComponent(context).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.chapter_fragment, container, false);
        ButterKnife.bind(this, view);
        chapterPresenter.attachView(this);

        if (getArguments() != null) {
            chapterPresenter.setChapterName(getArguments().getString(CHAPTER_NAME));
            chapterPresenter.setBibleName(getArguments().getString(BIBLE_NAME));
            chapterPresenter.setBooks(getArguments().getStringArrayList(BOOKS_NAME));
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        chapterPresenter.refreshChapterData();
    }

    void updateName(final String name) {
        nameView.setText(name);
    }

    void updateChapterText(final String chapterText) {
        chapterTextView.setText(chapterText);
    }

    void updateBookSelector(final List<String> books) {

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, books);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        bookSpinner.setAdapter(spinnerArrayAdapter);
    }

    void handleError(final Throwable throwable) {
        Toast.makeText(getActivity(), "Unexpected Error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        chapterPresenter.detachView();
    }

}

package com.example.mitchell.bible.feature;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.example.mitchell.bible.R;
import com.example.mitchell.bible.injection.InjectionHelper;
import com.example.mitchell.bible.service.model.BookModel;
import com.example.mitchell.bible.service.model.ChapterModel;
import com.example.mitchell.bible.view.PresenterView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PresenterView {

    private static final java.lang.String BOOK_INDEX = "BOOK_INDEX";

    @BindView(R.id.chapter_pager)
    ViewPager chapterPager;

    @BindView(R.id.book_spinner)
    Spinner bookSpinner;

    @Inject
    MainPresenter mainPresenter;

    int bookIndex;


    String bible = "LEB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectionHelper.getApplicationComponent(this).inject(this);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        bookSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bookIndex = i;
                mainPresenter.refreshChapters(bible, i);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

       mainPresenter.attachView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bible =  getIntent().getStringExtra("bible");
        mainPresenter.initiateBooks(bible);
        mainPresenter.refreshChapters(bible, bookIndex);
    }

    void displayChapters(final List<ChapterModel> chapters) {

        //Since in AppCompat Activity ensure you get the SupportFragmentManager, if using native Api use Fragment Manager
        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(final int position) {
                if(position == 0) {
                    return FavoritesFragment.newInstance();
                } else {
                    final ChapterModel response = chapters.get(position -1);
                    return ChapterFragment.newInstance(bible, response.getChapterName());
                }
            }

            @Override
            public int getCount() {
                return chapters.size();
            }

        };
        chapterPager.setAdapter(null);
        chapterPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    void updateBookSelector(final List<BookModel> books) {
        List<String> bookNames =Stream.of(books).map(BookModel::getBookName).collect(Collectors.toList());
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this.getApplicationContext(), android.R.layout.simple_spinner_item, bookNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        bookSpinner.setAdapter(spinnerArrayAdapter);
    }


    public void handleError(Throwable throwable) {
        Toast.makeText(this, "Unexpected Error", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

}

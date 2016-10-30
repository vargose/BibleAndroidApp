package com.example.mitchell.bible.feature;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.mitchell.bible.R;
import com.example.mitchell.bible.context.BibleApp;
import com.example.mitchell.bible.injection.InjectionHelper;
import com.example.mitchell.bible.service.data.BookModel;
import com.example.mitchell.bible.service.data.ChapterModel;
import com.example.mitchell.bible.view.PresenterView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PresenterView {

    @BindView(R.id.chapter_pager)
    ViewPager characterPager;

    @Inject
    MainPresenter mainPresenter;

    String bible = "LEB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectionHelper.getApplicationComponent(this).inject(this);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

       mainPresenter.attachView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bible =  getIntent().getStringExtra("bible");
        mainPresenter.refreshChapters(bible);
    }

    void displayChapters(final List<ChapterModel> chapters, final List<BookModel> books) {

        //Since in AppCompat Activity ensure you get the SupportFragmentManager, if using native Api use Fragment Manager
        characterPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(final int position) {

                final ChapterModel response = chapters.get(position);
                return ChapterFragment.newInstance(bible, response.getChapterName(), books);
            }

            @Override
            public int getCount() {
                return chapters.size();
            }
        });
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

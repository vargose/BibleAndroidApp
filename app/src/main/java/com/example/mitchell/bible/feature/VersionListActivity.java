package com.example.mitchell.bible.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.mitchell.bible.R;
import com.example.mitchell.bible.context.BaseApplication;
import com.example.mitchell.bible.service.model.VersionModel;
import com.example.mitchell.bible.view.ImageLoader;
import com.example.mitchell.bible.view.PresenterView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mitchell on 10/29/16.
 */

public class VersionListActivity extends AppCompatActivity implements PresenterView {

    @BindView(R.id.recyclerview) RecyclerView versionRecyclerView;

    @Inject
    VersionListPresenter presenter;
    @Inject
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_list);
        ((BaseApplication) getApplicationContext()).getComponent().inject(this);
        ButterKnife.bind(this);

        presenter.attachView(this);

        versionRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        versionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        presenter.refreshChapters();
    }

    public void showError() {
        Toast.makeText(this, "Error Loading comics", Toast.LENGTH_LONG).show();
    }

    public void showVersions(List<VersionModel> data) {
        versionRecyclerView.setAdapter(new VersionListAdapter(this, data, imageLoader, presenter));
    }

    public void startVersionCardsActivity(String bible){
        final Intent intent = new Intent(VersionListActivity.this, MainActivity.class);
        intent.putExtra("bible", bible);
        startActivity(intent);
    }
}
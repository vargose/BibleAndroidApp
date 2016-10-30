package com.example.mitchell.bible.feature;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mitchell.bible.R;
import com.example.mitchell.bible.service.data.VersionModel;
import com.example.mitchell.bible.view.ImageLoader;

import java.util.List;

/**
 * Created by mitchell on 10/29/16.
 */

public class VersionListAdapter extends RecyclerView.Adapter<VersionViewHolder> {
    private final VersionListActivity versionListActivity;
    private final List<VersionModel> data;
    private ImageLoader imageLoader;
    private VersionListPresenter versionListPresenter;

    public VersionListAdapter(final VersionListActivity versionListActivity, final List<VersionModel> data, final ImageLoader imageLoader, final VersionListPresenter versionListPresenter) {
        this.versionListActivity = versionListActivity;
        this.data = data;
        this.imageLoader = imageLoader;
        this.versionListPresenter = versionListPresenter;
    }

    @Override
    public void onBindViewHolder(final VersionViewHolder holder, final int position) {
        holder.bindView(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public VersionViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.version_row, parent, false);
        return new VersionViewHolder(view, imageLoader, versionListPresenter);
    }
}

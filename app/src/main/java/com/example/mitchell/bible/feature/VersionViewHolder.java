package com.example.mitchell.bible.feature;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mitchell.bible.R;
import com.example.mitchell.bible.service.data.VersionModel;
import com.example.mitchell.bible.view.ImageLoader;

/**
 * Created by mitchell on 10/29/16.
 */

public class VersionViewHolder extends RecyclerView.ViewHolder {

    private final ImageView image;
    private final TextView name;
    private final ImageLoader imageLoader;
    private final VersionListPresenter presenter;

    public VersionViewHolder(final View itemView, final ImageLoader imageLoader, final VersionListPresenter presenter) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.version_list_image);
        name = (TextView) itemView.findViewById(R.id.version_list_name);
        this.imageLoader = imageLoader;
        this.presenter = presenter;
    }

    public void bindView(VersionModel version) {
        imageLoader.loadImageIntoView(version.getImageUrl(), image);
        name.setText(version.getTitle());
        itemView.setOnClickListener(v -> presenter.versionClicked(version.getBible()));
    }
}

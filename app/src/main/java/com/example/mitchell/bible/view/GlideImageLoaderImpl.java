package com.example.mitchell.bible.view;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideImageLoaderImpl implements ImageLoader {

    private final Context context;

    public GlideImageLoaderImpl(final Context context) {
        this.context = context;
    }

    @Override
    public void loadImageIntoView(final String url, final ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }
}

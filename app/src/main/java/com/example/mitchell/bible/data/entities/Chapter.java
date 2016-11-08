package com.example.mitchell.bible.data.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by mitchell on 10/30/16.
 */

public class Chapter {
    private final String chapterName;
    private boolean favorite = false;

    public Chapter(final String chapterName) {
        this.chapterName = chapterName;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public String getChapterName() {return chapterName;}

    public void setFavorite(final boolean favorite) {
        this.favorite = favorite;
    }

    public static final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder chapterName(final String chapterName) {
            values.put(Entity.COLUMN_NAME, chapterName);
            return this;
        }

        public Builder favorite(final boolean favorite) {
            values.put(Entity.COLUMN_FAVORITE, favorite ? 1 : 0);
            return this;
        }

        public ContentValues build() {
            return values;
        }
    }

    public static final Func1<? super SqlBrite.Query, ? extends List<Chapter>> QUERY_MAP = new Func1<SqlBrite.Query, List<Chapter>>() {
        @Override
        public List<Chapter> call(final SqlBrite.Query query) {
            final List<Chapter> chapterList = new ArrayList<>();

            try (final Cursor cursor = query.run()) {
                if (cursor != null) {

                    while (cursor.moveToNext()) {
                        final String name = cursor.getString(cursor.getColumnIndexOrThrow(Entity.COLUMN_NAME));

                        final Chapter chapter = new Chapter(name);

                        final int favorite = cursor.getInt(cursor.getColumnIndexOrThrow(Entity.COLUMN_FAVORITE));
                        chapter.setFavorite(favorite > 0);

                        chapterList.add(chapter);
                    }
                }
            }

            return chapterList;
        }
    };

    public static class Entity implements BaseColumns {
        public static final String TABLE_NAME = "chapter";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_FAVORITE = "favorite";
    }
}

package com.example.mitchell.bible.data;


import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.mitchell.bible.data.entities.Chapter;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.schedulers.Schedulers;

public class ObservableDatabase {

    public static final String QUERY_CHAPTERS = "SELECT * FROM " + Chapter.Entity.TABLE_NAME;
    private final BriteDatabase briteDatabase;

    public ObservableDatabase(final Context context) {
        final SqlBrite instance = new SqlBrite.Builder().logger(message -> Log.d(ObservableDatabase.class.getSimpleName(), message)).build();
        briteDatabase = instance.wrapDatabaseHelper(new ChapterDbOpenHelper(context), Schedulers.io());
        briteDatabase.setLoggingEnabled(true);
    }

    public void insertChapter(final ContentValues contentValues) {
        briteDatabase.insert(Chapter.Entity.TABLE_NAME, contentValues);
    }

    public void updateChapter(final String chapterName, final ContentValues contentValues) {
        int rowsAffected = briteDatabase.update(Chapter.Entity.TABLE_NAME, contentValues, Chapter.Entity.COLUMN_NAME + " = ?", chapterName);
        if(rowsAffected == 0){
            insertChapter(contentValues);
        }
    }

    public Observable<List<Chapter>> observeChapters() {
        return briteDatabase.createQuery(Chapter.Entity.TABLE_NAME, QUERY_CHAPTERS)
                            .map(Chapter.QUERY_MAP);
    }

    public Observable<Chapter> observeChapter(final String chapterName) {
        return briteDatabase.createQuery(Chapter.Entity.TABLE_NAME, QUERY_CHAPTERS + " WHERE " + Chapter.Entity.COLUMN_NAME + " = '" + chapterName + "'")
                            .map(Chapter.QUERY_MAP)
                            .filter((chapters) -> chapters.size() == 1)
                            .map((chapters) -> chapters.get(0));
    }


}

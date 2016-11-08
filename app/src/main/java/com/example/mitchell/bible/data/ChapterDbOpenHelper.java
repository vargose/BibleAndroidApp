package com.example.mitchell.bible.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mitchell.bible.data.entities.Chapter;

public class ChapterDbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "chapter.db";
    private static final int DB_VERSION = 1;

    private static final String CREATE_CHAPTER_TABLE =
            "CREATE TABLE " + Chapter.Entity.TABLE_NAME
                    + " ("
                    + Chapter.Entity.COLUMN_NAME + " TEXT,"
                    + Chapter.Entity.COLUMN_FAVORITE + " INTEGER DEFAULT 0"
                    + ")";

    public ChapterDbOpenHelper(final Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(CREATE_CHAPTER_TABLE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
//        db.execSQL("ALTER TABLE " + Chapter.Entity.TABLE_NAME + " ADD COLUMN " + Chapter.Entity.COLUMN_FAVORITE + " INTEGER DEFAULT 0;");
    }

    @Override
    public void onDowngrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);

        // Should keep onUpgrade and onDowngrade in sync
    }
}

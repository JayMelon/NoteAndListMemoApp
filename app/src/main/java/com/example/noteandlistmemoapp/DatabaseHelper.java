package com.example.noteandlistmemoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "noteandlistmemos.db";
    private static final int DATABASE_VERSION = 1;
    public static final String NOTES_TABLE = "NOTES_TABLE";
    public static final String ID = "COLUMN_NOTE_ID";
    public static final String TITLE = "COLUMN_TITLE";
    public static final String CONTENT = "COLUMN_CONTENT";
    public static final String PRIORITY = "COLUMN_PRIORITY";
    public static final String CREATION_TIME = "COLUMN_CREATION_TIME";
    public static final String DUE_TIME = "COLUMN_DUE_TIME";

    private static final String CREATE_TABLE = "CREATE TABLE " + NOTES_TABLE + " (" + ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE  + " TEXT NOT NULL, " + CONTENT + " TEXT, " +
            PRIORITY + " TEXT, " + CREATION_TIME + " TEXT NOT NULL, " + DUE_TIME + " TEXT )";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int j) {
        Log.w(DatabaseHelper.class.getName(), "Upgrading database from version " + i + " To" + j + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TITLE);
        onCreate(database);
    }
}
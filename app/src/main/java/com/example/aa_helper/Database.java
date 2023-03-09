package com.example.aa_helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class Database extends SQLiteOpenHelper {

    private Context context;
    public static final String DB_NAME = "SongList.db";
    public static final int DB_VER = 1;
    public static final String TABLE_NAME = "song";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "song_title";
    public static final String COLUMN_LYRICIST = "song_lyricist";
    public static final String COLUMN_COMPOSER = "song_composer";
    public static final String COLUMN_ARRANGER = "song_arranger";
    public static final String COLUMN_MIXER = "song_mixer";
    public static final String COLUMN_LYRICS = "song_lyrics";

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_LYRICIST + " TEXT ," +
                        COLUMN_COMPOSER  + " TEXT ," +
                        COLUMN_ARRANGER  + " TEXT ," +
                        COLUMN_MIXER  + " TEXT ," +
                        COLUMN_LYRICS  + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

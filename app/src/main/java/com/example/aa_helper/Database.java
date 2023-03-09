package com.example.aa_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

class Database extends SQLiteOpenHelper {

    private Context context;
    public static final String DB_NAME = "SongList.db";
    public static final int DB_VER = 1;
    public static final String TABLE_NAME = "song";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "song_title";
    public static final String COLUMN_SINGER = "song_singer";
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
                        COLUMN_SINGER + " TEXT, " +
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

    void addSong(String title, String singer, String lyricist){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_SINGER,singer);
        cv.put(COLUMN_LYRICIST,lyricist);
        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1){
            Log.d("DB","failed to add to db");
            Toast.makeText(context, "Failed",Toast.LENGTH_SHORT).show();
        }
    }

    Cursor read_db(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }
}

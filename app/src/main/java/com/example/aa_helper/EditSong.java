package com.example.aa_helper;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class EditSong extends AppCompatActivity {
    String lyrics;
    String sname;
    Cursor cursor;
    EditText lywrite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_song);
        //get title
        TextView text = findViewById(R.id.selected_name);
        sname = getIntent().getStringExtra("song");
        text.setText(sname);
        //query song data
        Database db = new Database(EditSong.this);
        cursor = db.get_song(sname);
        cursor.moveToNext();
        //get lyrics
        lyrics = cursor.getString(7);
        //set edittext
        lywrite = findViewById(R.id.lyrics);
        lywrite.setText(lyrics);
    }

    protected void onStop(){
        super.onStop();
        Database db = new Database(EditSong.this);
        lyrics = lywrite.getText().toString();
        db.update_lyrics(sname,lyrics);
    }

}
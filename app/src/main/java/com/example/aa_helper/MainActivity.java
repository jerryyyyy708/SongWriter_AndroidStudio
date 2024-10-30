package com.example.aa_helper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.SharedPreferencesKt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Activity_Dialog.Dialog_Listener{
    FloatingActionButton add_trip;
    Database db;
    ArrayList<String> song_list = new ArrayList<String>();
    ArrayList<String> singer_list = new ArrayList<String>();
    ArrayList<String> lyricist_list = new ArrayList<String>();
    ListView listview;
    @Override
    public void applyTexts(String originalName, String newName, String singer, String lyricist) {
        Cursor cursor = db.get_song(originalName);
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            // 更新歌曲資料
            db.updateSong(originalName, newName, singer, lyricist);
            Toast.makeText(this, "Song updated!", Toast.LENGTH_SHORT).show();
        } else {
            // 新增歌曲
            db.addSong(newName, singer, lyricist);
            Toast.makeText(this, "Song added!", Toast.LENGTH_SHORT).show();
        }
        store_data();
        if (cursor != null) {
            cursor.close();
        }
    }


    public void openDialogWithData(String song, String singer, String lyricist) {
        Activity_Dialog dialog = new Activity_Dialog();
        dialog.setInitialValues(song, singer, lyricist);
        dialog.show(getSupportFragmentManager(), "Edit Song");
    }

    //int click = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 設置 Toolbar 為 ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //song list creation
        listview = (ListView) findViewById(R.id.Activity_List);

        //database creation
        db = new Database(MainActivity.this);

        // 初始化 Spinner
        Spinner singerSpinner = findViewById(R.id.singer_spinner);
        ArrayList<String> singerOptions = new ArrayList<>();
        singerOptions.add("All"); // 添加 "All" 選項

        // 獲取資料庫中的歌手列表並添加到選項
        Cursor singerCursor = db.getAllSingers();
        while (singerCursor.moveToNext()) {
            String singerName = singerCursor.getString(0);
            if (!singerOptions.contains(singerName)) {
                singerOptions.add(singerName);
            }
        }
        singerCursor.close();

        // 設置 Spinner 的 Adapter
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, singerOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        singerSpinner.setAdapter(spinnerAdapter);

        // 設置 Spinner 的選擇事件
        singerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSinger = singerOptions.get(position);
                if (selectedSinger.equals("All")) {
                    store_data(); // 顯示所有歌曲
                } else {
                    filterDataBySinger(selectedSinger); // 根據歌手篩選歌曲
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        store_data();

        //song list onclick
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,EditSong.class);
                String selected_song = (String) adapterView.getItemAtPosition(i);
                intent.putExtra("song",selected_song);
                startActivity(intent);
            }
        });

        listview.setOnItemLongClickListener((parent, view, position, id) -> {
            String selectedSong = song_list.get(position);
            String selectedSinger = singer_list.get(position);
            String selectedLyricist = lyricist_list.get(position);

            openDialogWithData(selectedSong, selectedSinger, selectedLyricist);
            return true;
        });

        //set button
        add_trip = findViewById(R.id.Add_Trip);
        add_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        //first time add song1
        SharedPreferences pref = getSharedPreferences("prefs",MODE_PRIVATE);
        boolean firstStart = pref.getBoolean("firstStart",true);
        if(firstStart){
            //query add default
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("firstStart",false);
            editor.apply();
        }
    }
    public void openDialog() {
        Activity_Dialog new_activity = new Activity_Dialog();
        new_activity.show(getSupportFragmentManager(),"New Song");
    }

    void filterDataBySinger(String singer) {
        Cursor cursor = db.getSongsBySinger(singer);
        song_list.clear();
        singer_list.clear();
        lyricist_list.clear();

        while (cursor.moveToNext()) {
            song_list.add(cursor.getString(1));  // 歌曲名稱
            singer_list.add(cursor.getString(2));  // 歌手名稱
            lyricist_list.add(cursor.getString(3));  // 作詞者名稱
        }
        cursor.close();

        SongAdapter songAdapter = new SongAdapter(this, song_list, singer_list);
        listview.setAdapter(songAdapter);
    }

    void store_data() {
        Cursor cursor = db.read_db();
        song_list = new ArrayList<>();
        singer_list = new ArrayList<>();
        lyricist_list = new ArrayList<>();

        while (cursor.moveToNext()) {
            song_list.add(cursor.getString(1));  // 歌曲名稱
            singer_list.add(cursor.getString(2));  // 歌手名稱
            lyricist_list.add(cursor.getString(3));  // 作詞者名稱
        }

        SongAdapter songAdapter = new SongAdapter(this, song_list, singer_list);
        listview.setAdapter(songAdapter);
    }

}



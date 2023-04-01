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
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public void applyTexts(String name, String singer, String lyricist) {
        if(name.length()==0) {
            Toast.makeText(MainActivity.this, "Empty Name!", Toast.LENGTH_LONG).show();
        }
        else {
            Database db = new Database(MainActivity.this);
            db.addSong(name,singer,lyricist);
            store_data();
        }
    }

    //int click = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //database creation
        db = new Database(MainActivity.this);
        store_data();
        //song list creation
        listview = (ListView) findViewById(R.id.Activity_List);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list_view,R.id.song_text,song_list);
        listview.setAdapter(arrayAdapter);
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
        new_activity.show(getSupportFragmentManager(),"New Activity");
    }

    void store_data(){
        Cursor cursor = db.read_db();
        song_list = new ArrayList<String>();
        singer_list = new ArrayList<String>();
        lyricist_list = new ArrayList<String>();
        while(cursor.moveToNext()){
            listview = (ListView) findViewById(R.id.Activity_List);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list_view,R.id.song_text,song_list);
            listview.setAdapter(arrayAdapter);
            Log.d("DB",cursor.getString(1));
            song_list.add(cursor.getString(1));
            singer_list.add(cursor.getString(2));
            lyricist_list.add(cursor.getString(3));
        }
    }

}



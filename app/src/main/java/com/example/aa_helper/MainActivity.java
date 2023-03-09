package com.example.aa_helper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.SharedPreferencesKt;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Activity_Dialog.Dialog_Listener{
    FloatingActionButton add_trip;
    ArrayList<String> song_list = new ArrayList<String>();
    ListView listview;
    @Override
    public void applyTexts(String name) {
        if(name.length()==0) {
            Toast.makeText(MainActivity.this, "Empty Name!", Toast.LENGTH_LONG).show();
        }
        else {
            song_list.add(name);
        }
    }

    //int click = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //song list creation
        listview = (ListView) findViewById(R.id.Activity_List);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list_view,R.id.song_text,song_list);
        listview.setAdapter(arrayAdapter);

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
            song_list.add("Song1");
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("firstStart",false);
            editor.apply();
        }
    }
    public void openDialog() {
        Activity_Dialog new_activity = new Activity_Dialog();
        new_activity.show(getSupportFragmentManager(),"New Activity");
    }
}



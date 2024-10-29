package com.example.aa_helper;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;



public class SongAdapter extends ArrayAdapter<String> {
    private final ArrayList<String> songList;
    private final ArrayList<String> singerList;
    private final Context context;

    public SongAdapter(Context context, ArrayList<String> songList, ArrayList<String> singerList) {
        super(context, R.layout.activity_list_view, songList);
        this.context = context;
        this.songList = songList;
        this.singerList = singerList;
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_list_view, parent, false);

        TextView songText = rowView.findViewById(R.id.song_text);
        TextView singerText = rowView.findViewById(R.id.singer_text);

        songText.setText(songList.get(position));
        singerText.setText(singerList.get(position));

        return rowView;
    }
}

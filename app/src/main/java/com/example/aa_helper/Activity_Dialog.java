package com.example.aa_helper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Activity_Dialog extends AppCompatDialogFragment {
    private EditText nActivity, singer, lyricist;
    private Dialog_Listener listener;
    private String initialSong, initialSinger, initialLyricist;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(view).setTitle("Edit Song")
                .setNegativeButton("Cancel", (dialogInterface, i) -> { /* No action on cancel */ })
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    String name = nActivity.getText().toString();
                    String singerName = singer.getText().toString();
                    String lyricistName = lyricist.getText().toString();
                    listener.applyTexts(initialSong, name, singerName, lyricistName);
                });

        nActivity = view.findViewById(R.id.editTextActivity);
        singer = view.findViewById(R.id.singer_name);
        lyricist = view.findViewById(R.id.lyricist_name);

        // Set initial values
        nActivity.setText(initialSong);
        singer.setText(initialSinger);
        lyricist.setText(initialLyricist);

        return builder.create();
    }

    public void setInitialValues(String song, String singer, String lyricist) {
        this.initialSong = song;
        this.initialSinger = singer;
        this.initialLyricist = lyricist;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (Dialog_Listener) context;
    }

    public interface Dialog_Listener {
        void applyTexts(String originalName, String newName, String singer, String lyricist);
    }
}

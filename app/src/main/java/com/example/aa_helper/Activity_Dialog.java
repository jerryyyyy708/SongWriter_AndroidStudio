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
    private EditText nActivity,singer,lyricist;
    private Dialog_Listener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(view).setTitle("New Activity")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = nActivity.getText().toString();
                        String singername = singer.getText().toString();
                        String lyricistname = lyricist.getText().toString();
                        listener.applyTexts(name,singername,lyricistname);
                    }
                });
        nActivity = view.findViewById(R.id.editTextActivity);
        singer = view.findViewById(R.id.singer_name);
        lyricist = view.findViewById(R.id.lyricist_name);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener =(Dialog_Listener) context;
    }

    public interface Dialog_Listener{
        void applyTexts(String name,String singer, String lyricist);
    }
}

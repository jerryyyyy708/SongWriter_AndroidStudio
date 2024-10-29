package com.example.aa_helper;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.ImageButton;
import androidx.appcompat.app.AlertDialog;
import android.widget.Toast;

public class EditSong extends AppCompatActivity {
    String lyrics;
    String sname;
    Cursor cursor;
    EditText lywrite;
    Database db; // 提升 db 為類別變數以便重複使用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_song);

        // 設置 Database 和其他 UI 元件
        db = new Database(EditSong.this);
        TextView text = findViewById(R.id.selected_name);
        sname = getIntent().getStringExtra("song");
        text.setText(sname);

        cursor = db.get_song(sname);
        cursor.moveToNext();
        lyrics = cursor.getString(7);

        lywrite = findViewById(R.id.lyrics);
        lywrite.setText(lyrics);

        // 設置刪除按鈕
        ImageButton deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view -> showDeleteConfirmationDialog());
    }

    private void showDeleteConfirmationDialog() {
        // 顯示確認刪除的提示對話框
        new AlertDialog.Builder(this)
                .setTitle("Delete Song?")
                .setMessage("Are you sure you want to delete this song?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", (dialogInterface, i) -> deleteSong())
                .show();
    }

    private void deleteSong() {
        // 刪除歌曲並返回主頁面
        db.delete_song(sname);
        Toast.makeText(this, "Song deleted", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop(){
        super.onStop();
        lyrics = lywrite.getText().toString();
        db.update_lyrics(sname, lyrics);
    }
}
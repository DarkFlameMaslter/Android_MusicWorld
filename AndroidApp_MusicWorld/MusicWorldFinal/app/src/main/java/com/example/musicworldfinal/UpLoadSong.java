package com.example.musicworldfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import com.example.musicworldfinal.models.AudioModel;

import java.io.File;
import java.util.ArrayList;

public class UpLoadSong extends AppCompatActivity {

    private RecyclerView UploadRecycleView;
    private TextView UploadNoMusicFound;
    private ArrayList<AudioModel> songsList = new ArrayList<>();

    public static final String CHANNEL_ID = "musicWorldApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_song);

        UploadRecycleView = findViewById(R.id.UpLoad_recycleView);
        UploadNoMusicFound = findViewById(R.id.UploadSong_NoSongFound);

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);
        while (cursor.moveToNext()) {
            AudioModel songData = new AudioModel(cursor.getString(1), cursor.getString(0), cursor.getString(2));
            if (new File(songData.getPath()).exists())
                songsList.add(songData);
        }

        if (songsList.size() == 0) {
            UploadNoMusicFound.setVisibility(View.VISIBLE);
        } else {
            //recyclerview
            UploadRecycleView.setLayoutManager(new LinearLayoutManager(UpLoadSong.this));
            UploadRecycleView.setAdapter(new UploadSongAdapter(songsList, getApplicationContext()));
        }
    }
}
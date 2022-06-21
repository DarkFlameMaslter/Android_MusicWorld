package com.example.musicworldfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.musicworldfinal.models.DBHelper;
import com.example.musicworldfinal.models.user;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private TextView welcomeUser;
    private TextView toMusicBoard,toLb, toUploadSongBt;
    private FloatingActionButton logoutB;

    private user Theuser;

    private String TAG = "MainAct";

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeUser = findViewById(R.id.Main_welcomeUser);

        dbHelper = new DBHelper(this);
        if(!dbHelper.check4User()){
            //go to login activity
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        } else {
            Theuser = dbHelper.getUSer();
            welcomeUser.setText("Welcome "+Theuser.getUsername());
        }

        toLb = findViewById(R.id.MainToLib);
        toLb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: pressed to Lib");
                Intent intent = new Intent(MainActivity.this, MusicLibrary_Main.class);
                startActivity(intent);
            }
        });
        
        toMusicBoard = findViewById(R.id.getMusicBoard);
        toMusicBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: pressed to Music Board");
                Intent intent = new Intent(MainActivity.this, MusicBoard_Main.class);
                startActivity(intent);
            }
        });

        logoutB = findViewById(R.id.logoutButton);
        logoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.userLogout();
                Log.i(TAG, "onClick: User LogOut");
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        toUploadSongBt = findViewById(R.id.upLoadSongButton);
        toUploadSongBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpLoadSong.class);
                startActivity(intent);

            }
        });

    }
}
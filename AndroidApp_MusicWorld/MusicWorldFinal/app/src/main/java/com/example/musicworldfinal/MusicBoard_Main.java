package com.example.musicworldfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.musicworldfinal.models.RESTapi;
import com.example.musicworldfinal.models.RetrofitClient;
import com.example.musicworldfinal.models.AudioModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicBoard_Main extends AppCompatActivity {

    private RecyclerView MusicBoard_recycleView;
    private TextView MusicBoard_NoSongFound;
    private ArrayList<AudioModel> onlineSongsList = new ArrayList<AudioModel>();

    private FloatingActionButton reloadB;

    public static final String TAG = "MusicBoard";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_board_main);

        MusicBoard_NoSongFound = findViewById(R.id.MusicBoard_NoSongText);
        MusicBoard_recycleView = findViewById(R.id.MusicBoard_RecycleView);


        RESTapi resTapi = RetrofitClient.getRetrofitInstance().create(RESTapi.class);
        Call<List<AudioModel>> call =  resTapi.getOnlineSongs();

        //get online list
        call.enqueue(new Callback<List<AudioModel>>() {
            @Override
            public void onResponse(Call<List<AudioModel>> call, Response<List<AudioModel>> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "onResponse: Look good");
                    List<AudioModel> theList = response.body();
                    onlineSongsList.addAll(theList);
                    Log.i(TAG, "onResponse: theList 0"+ theList.get(0).toString());

                } else {
                    Log.i(TAG, "onResponse: err! okey it's not succesfull. something goes wrong");
                }
            }

            @Override
            public void onFailure(Call<List<AudioModel>> call, Throwable t) {
                Log.i(TAG, "onFailure: Bad bro! "+t.toString());
            }
        });

        if(onlineSongsList.isEmpty()){
            MusicBoard_NoSongFound.setVisibility(View.VISIBLE);
        } else {
            MusicBoard_recycleView.setLayoutManager(new LinearLayoutManager(MusicBoard_Main.this));
            MusicBoard_recycleView.setAdapter(new MusicBoard_ViewAdapter(onlineSongsList,getApplicationContext()));
        }

        reloadB = findViewById(R.id.MusicBoardReload);
        reloadB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onlineSongsList.isEmpty()){
                    MusicBoard_NoSongFound.setVisibility(View.VISIBLE);
                    Log.i(TAG, "onClick: Empty list");
                } else {
                    MusicBoard_NoSongFound.setVisibility(View.INVISIBLE);
                    MusicBoard_recycleView.setLayoutManager(new LinearLayoutManager(MusicBoard_Main.this));
                    MusicBoard_recycleView.setAdapter(new MusicBoard_ViewAdapter(onlineSongsList,getApplicationContext()));
                }
            }
        });

    }

    public boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + "song.mp3");

            Log.i(TAG, "writeResponseBodyToDisk: "+futureStudioIconFile.getPath());
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

//                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
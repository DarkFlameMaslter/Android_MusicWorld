package com.example.musicworldfinal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.musicworldfinal.models.AudioModel;
import com.example.musicworldfinal.models.RESTapi;
import com.example.musicworldfinal.models.RetrofitClient;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadSongAdapter extends RecyclerView.Adapter<UploadSongAdapter.TheViewHolder> {

    private String TAG = "UploadSongAdapter";

    public static final String SONG_KEY = "songUpload";

    ArrayList<AudioModel> songsList;
    Context context;

    public UploadSongAdapter(ArrayList<AudioModel> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;
    }

    @Override
    public TheViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.upload_recycleview_itemadapter, parent, false);
        return new UploadSongAdapter.TheViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UploadSongAdapter.TheViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AudioModel songData = songsList.get(position);

        holder.UPloadtitleTextView.setText(songData.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to another acitivty
                RESTapi resTapi = RetrofitClient.getRetrofitInstance().create(RESTapi.class);

                Uri muri = Uri.fromFile(new File(songsList.get(position).getPath()));
                File theFile = new File(songsList.get(position).getPath());

                RequestBody requestBodySong = RequestBody.create(MediaType.parse("multipart/form-data"), theFile);

                MultipartBody.Part multipartBodySong = MultipartBody.Part.createFormData(SONG_KEY, theFile.getName(), requestBodySong);

                Call<ResponseBody> call = resTapi.upMusic4Share(multipartBodySong);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.i(TAG, "onResponse: Yep seem to be fine");
                        Toast.makeText(context, "Song uploaded!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i(TAG, "onFailure: There is errr "+t.toString() );
                        Toast.makeText(context, "Something Gone wrong on the way up!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class TheViewHolder extends RecyclerView.ViewHolder {

        TextView UPloadtitleTextView;

        public TheViewHolder(View itemView) {
            super(itemView);
            UPloadtitleTextView = itemView.findViewById(R.id.UploadMusicTitle);
        }
    }
}
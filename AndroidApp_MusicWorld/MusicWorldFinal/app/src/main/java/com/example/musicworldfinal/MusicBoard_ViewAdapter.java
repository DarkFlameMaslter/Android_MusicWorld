package com.example.musicworldfinal;

import android.content.Context;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicworldfinal.models.AudioModel;
import com.example.musicworldfinal.models.RESTapi;
import com.example.musicworldfinal.models.RetrofitClient;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicBoard_ViewAdapter extends RecyclerView.Adapter<MusicBoard_ViewAdapter.deViewHolder> {

    ArrayList<AudioModel> theonlineList;
    Context mContext;

    private String TAG = "musicBoardAdapter";

    public MusicBoard_ViewAdapter(ArrayList<AudioModel> onlineSongsList, Context context){
        theonlineList = onlineSongsList;
        mContext = context;
    }

    @NonNull
    @Override
    public MusicBoard_ViewAdapter.deViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.musicboard_adapter_view,parent,false);
        return new MusicBoard_ViewAdapter.deViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MusicBoard_ViewAdapter.deViewHolder deviewHolder, int position) {

        AudioModel songData = theonlineList.get(position);
        deviewHolder.detitleTextView.setText(songData.getTitle());
        deviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RESTapi resTapi = RetrofitClient.getRetrofitInstance().create(RESTapi.class);
                Call<ResponseBody> call = resTapi.getMusic(songData.getTitle());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Log.i(TAG, "onResponse: Look good on getting song");
                            if(writeResponseBodyToDisk(response.body(), songData.getTitle())){
                                Log.i(TAG, "doInBackground: Done here?");
                            } else{
                                Log.i(TAG, "doInBackground: Something goes wrond");
                                Toast.makeText(mContext, "Error on response!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.i(TAG, "onResponse: oh no downloading is false");
                            Toast.makeText(mContext, "Error on download song!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i(TAG, "onFailure: You dead!");
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return theonlineList.size();
    }

    public class deViewHolder extends RecyclerView.ViewHolder{

        TextView detitleTextView;

        public deViewHolder(View itemView){
            super(itemView);
            detitleTextView = itemView.findViewById(R.id.MusicBoard_TitleTextView);
        }

    }

    public boolean writeResponseBodyToDisk(ResponseBody body, String songName) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(mContext.getExternalFilesDir(null) + File.separator + songName+".mp3");

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
                Toast.makeText(mContext, "Song Saved in"+mContext.getExternalFilesDir(null) + File.separator + songName+".mp3", Toast.LENGTH_SHORT).show();
                return true;
            } catch (IOException e) {
                Toast.makeText(mContext, "Error saving song!", Toast.LENGTH_SHORT).show();
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

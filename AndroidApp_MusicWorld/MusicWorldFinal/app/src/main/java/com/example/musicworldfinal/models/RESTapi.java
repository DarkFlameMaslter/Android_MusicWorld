package com.example.musicworldfinal.models;

import com.example.musicworldfinal.models.user;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RESTapi {
//    http://192.168.27.103/HololiveCA/users
    @POST("HololiveCA/user")
    Call<List<user>> Login(@Query("userLoginId") String userLoginId, @Query("userLoginPassword") String userLoginPassword);

    @GET("OnlineSongList")
    Call<List<AudioModel>> getOnlineSongs();

    @Multipart
    @POST("upload")
    Call<ResponseBody> upMusic4Share(
            @Part MultipartBody.Part file
    );

    @GET("download")
    Call<ResponseBody> getMusic(@Query("title") String deTitle);

}

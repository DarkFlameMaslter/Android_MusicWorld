package com.example.musicworldfinal.models;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.session.MediaSessionManager;
import android.os.Build;

import androidx.core.app.NotificationManagerCompat;

public class NotificationControler extends Application {
    public static final String CHANNEL_ID = "musicWorldApp";

    public static final String ACTION_PREV = "act_pre";
    public static final String ACTION_PP = "act_play_pause";
    public static final String ACTION_NEXT = "act_next";

    public static Notification notification;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationController();
    }

    public void createNotificationController(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Music Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if(manager!= null){
                manager.createNotificationChannel(channel);
            }
        }
    }


}

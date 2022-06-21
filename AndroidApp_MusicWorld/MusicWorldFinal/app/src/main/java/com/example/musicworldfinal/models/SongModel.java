package com.example.musicworldfinal.models;

public class SongModel {
    private String title,theSong;

    public SongModel(String title, String duration, String theSong) {
        this.title = title;
        this.theSong = theSong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getTheSong() {
        return theSong;
    }

    public void setTheSong(String theSong) {
        this.theSong = theSong;
    }
}

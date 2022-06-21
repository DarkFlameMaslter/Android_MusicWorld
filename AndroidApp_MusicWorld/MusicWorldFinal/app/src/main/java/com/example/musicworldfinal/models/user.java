package com.example.musicworldfinal.models;

public class user {
    private int id, priv;
    private String username, userLoginId, userLoginPassword;

    public user(int id, String username, String userLoginId, String userLoginPassword, int priv) {
        this.id = id;
        this.priv = priv;
        this.username = username;
        this.userLoginId = userLoginId;
        this.userLoginPassword = userLoginPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriv() {
        return priv;
    }

    public void setPriv(int priv) {
        this.priv = priv;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getUserLoginPassword() {
        return userLoginPassword;
    }

    public void setUserLoginPassword(String userLoginPassword) {
        this.userLoginPassword = userLoginPassword;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", priv=" + priv +
                ", username='" + username + '\'' +
                ", userLoginId='" + userLoginId + '\'' +
                ", userLoginPassword='" + userLoginPassword + '\'' +
                '}';
    }
}

package com.android.onroad.beans;

import android.net.Uri;

public class User {
    private int userId;
    private String username;
    private String emailAddress;
    private String password;
    private Uri photoUri;

    public User() {
    }

    public User(int userId, String username, String emailAddress, String password, Uri photoUri) {
        this.userId = userId;
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.photoUri = photoUri;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }
}
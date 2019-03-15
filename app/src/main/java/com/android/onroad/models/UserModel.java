package com.android.onroad.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserModel {

    private String name;
    private String phone;
    private String profile_image;
    private String security_level;
    private String user_id;

    public UserModel() {
    }

    public UserModel(String name, String phone, String profile_image, String security_level, String user_id) {
        this.name = name;
        this.phone = phone;
        this.profile_image = profile_image;
        this.security_level = security_level;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getSecurity_level() {
        return security_level;
    }

    public void setSecurity_level(String security_level) {
        this.security_level = security_level;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", security_level='" + security_level + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
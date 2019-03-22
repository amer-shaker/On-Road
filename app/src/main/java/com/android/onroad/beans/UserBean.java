package com.android.onroad.beans;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserBean {

    private String userId;
    private String name;
    private String email;
    private String phone;
    private String photoUrl;
    private String securityLevel;

    public UserBean() {

    }

    public UserBean(String userId, String name, String email, String phone, String photoUrl, String securityLevel) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.photoUrl = photoUrl;
        this.securityLevel = securityLevel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", securityLevel='" + securityLevel + '\'' +
                '}';
    }
}
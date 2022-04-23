package com.example.hospitalapplication.hospital_application;

public class User {
    String name;
    String email;
    String uid;
    String imageUrl;

    User() {

    }

    public User(String name, String email, String uid, String imageUrl) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.imageUrl = imageUrl;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

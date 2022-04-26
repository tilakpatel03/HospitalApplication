package com.example.hospitalapplication.hospital_application;

public class Doctor {
    String name;
    String email;
    String photoUrl;
    String expertise;
    String address;
    String hour;
    String uid;

    @Override
    public String toString() {
        return "Doctor" +
                "\nname      ='" + name +
                "\nemail     ='" + email +
                "\nexpertise ='" + expertise +
                "\naddress   ='" + address +
                "\nhour      ='" + hour;
    }

    Doctor(){

    }

    public Doctor(String name, String email, String photoUrl, String expertise, String address, String hour, String uid) {
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
        this.expertise = expertise;
        this.address = address;
        this.hour = hour;
        this.uid = uid;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}



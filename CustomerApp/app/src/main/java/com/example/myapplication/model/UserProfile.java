package com.example.myapplication.model;

import java.io.Serializable;

public class UserProfile implements Serializable {

    private String User_name , Email , Mobile , Address ;

    public UserProfile() {
    }

    public UserProfile(String user_name, String email, String mobile, String address) {
        User_name = user_name;
        Email = email;
        Mobile = mobile;
        Address = address;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}

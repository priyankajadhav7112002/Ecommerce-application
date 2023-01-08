package com.example.ecommerceapp.model;

public class User {
    public String id = "";
    String userName = "";
    String email = "";
    String image = "";
    String gender = "";
    Long mobile = 0l;
    int profileCompleted = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public int getProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(int profileCompleted) {
        this.profileCompleted = profileCompleted;
    }

    public User(String id, String userName, String email) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.image = image;
        this.gender = gender;
        this.mobile = mobile;
        this.profileCompleted = profileCompleted;
    }
}

package com.example.a165727.pedofitexerciseproject.UserProfile;

public class User {

    private String userID;
    private String height;
    private String weight;
    private String age;
    private String nickname;

    public User(String userID, String height, String weight, String age, String nickname) {
        this.userID = userID;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.nickname = nickname;
    }

    public User() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

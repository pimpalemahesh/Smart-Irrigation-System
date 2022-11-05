package com.myinnovation.smartirrigationsystem.Modals;

public class User {
    private String userId, userName, userMobileNumber;

    public User(String userId, String userName, String userMobileNumber) {
        this.userId = userId;
        this.userName = userName;
        this.userMobileNumber = userMobileNumber;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobileNumber() {
        return userMobileNumber;
    }

    public void setUserMobileNumber(String userMobileNumber) {
        this.userMobileNumber = userMobileNumber;
    }
}

package com.example.bookmarket;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("userId")
    private String userId;
    @SerializedName("sessionId")
    private String sessionId;


    public LoginResponse(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public String getSessionId() { return sessionId;}

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
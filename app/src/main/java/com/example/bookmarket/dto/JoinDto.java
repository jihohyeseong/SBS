package com.example.bookmarket.dto;


public class JoinDto {
    private String username;
    private String password;
    private String email;
    private String phoneNum;
    private String address;

    public JoinDto(String username, String password, String email, String phoneNum, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
    }
    // Getter methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getAddress() {
        return address;
    }

    // Setter methods
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}



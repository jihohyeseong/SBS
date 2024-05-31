package com.example.bookmarket.dto;

public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNum;
    private String address;
    private String role;

    // 생성자
    public User(Long id, String username, String password, String email, String phoneNum, String address, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
        this.role = role;
    }
    public User() {
        // 매개변수 없는 생성자
    }

    // 게터와 세터
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
package com.example.bookmarket;

import com.google.gson.annotations.SerializedName;

public class JoinData {
    /**
     * SerializedName 으로 JSON 객체와 해당 변수를 매칭
     * @SerializedName 괄호 안에는 해당 JSON 객체의 변수 명 적기
     * 이때, POST 매핑으로 받아올 값은, 굳이 annotation 을 붙이지 않고, JSON 객체의 변수명과 일치하는 변수만 선언하면 됨
     */

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("email")
    private String email;
    @SerializedName("phoneNum")
    private String phoneNum;
    @SerializedName("address")
    private String address;

    public JoinData(String username, String password, String email, String phoneNum, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
    }
}


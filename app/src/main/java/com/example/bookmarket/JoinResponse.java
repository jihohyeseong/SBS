package com.example.bookmarket;

import com.google.gson.annotations.SerializedName;

public class JoinResponse {

    @SerializedName("code")
    private int code; // 코드

    @SerializedName("message")
    private String message; // 메시지

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}






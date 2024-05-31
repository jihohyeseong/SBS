package com.example.bookmarket;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleCookieJar implements CookieJar {
    private List<Cookie> cookies;

    public SimpleCookieJar() {
        cookies = new ArrayList<>();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        this.cookies = new ArrayList<>(cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        if (cookies == null) {
            return new ArrayList<>();
        }
        return cookies;
    }
}

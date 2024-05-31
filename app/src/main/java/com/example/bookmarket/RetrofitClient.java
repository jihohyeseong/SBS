package com.example.bookmarket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
   // private static final String BASE_URL = "http://10.0.2.2:8080/";
    //private static final String BASE_URL = "http://172.20.10.2:8080/";
    private static final String BASE_URL = "http://52.79.46.118:8080/";


    public static Retrofit getRetrofitInstance() {
        Gson gson = new GsonBuilder().setLenient().create();
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .cookieJar(new SimpleCookieJar()) // CookieJar 추가
                    .addInterceptor(logging)
                    .build();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client) // OkHttpClient 사용
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
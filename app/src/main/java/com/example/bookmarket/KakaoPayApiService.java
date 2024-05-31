package com.example.bookmarket;

import com.example.bookmarket.dto.CartDto;
import com.example.bookmarket.dto.KakaoPayReadyResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

public interface KakaoPayApiService {

    //@Headers("Authorization: KakaoAK ${DEVB441AED22C91C0F24459E8D6EDB295FD44A88}")
    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("/payment/ready")
    Call<KakaoPayReadyResponse> readyToPay(@Body List<CartDto> cartDto);
}

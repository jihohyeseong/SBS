package com.example.bookstore.service;

import com.example.bookstore.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayService {

    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드
    static final String admin_Key = "DEVB441AED22C91C0F24459E8D6EDB295FD44A88"; // 공개 조심! 본인 애플리케이션의 어드민 키를 넣어주세요
    private KakaoReadyResponse kakaoReady;

    public KakaoReadyResponse kakaoPayReady(List<CartDto> cartDto, String username){
        String orderId = username + cartDto.get(0).getBookname();
        String itemName = (cartDto.size() == 1)? cartDto.get(0).getBookname() :cartDto.get(0).getBookname() + " 그 외 " + (cartDto.size()-1) + "권";
        Long quantity = 0L;
        Long price = 0L;
        for(CartDto cart : cartDto){
            quantity += cart.getQuantity();
            price += cart.getPrice() * cart.getQuantity();
        }
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("cid", cid);
        parameters.put("partner_order_id", orderId);
        parameters.put("partner_user_id", username);
        parameters.put("item_name", itemName);
        parameters.put("quantity", String.valueOf(quantity));
        parameters.put("total_amount", String.valueOf(price));
        parameters.put("vat_amount", String.valueOf(0));
        parameters.put("tax_free_amount", String.valueOf(0));
        parameters.put("approval_url", "http://52.79.46.118/purchase/success");
        parameters.put("cancel_url", "http://52.79.46.118/cart");
        parameters.put("fail_url", "http://52.79.46.118/books");

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoReady = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/ready",
                requestEntity,
                KakaoReadyResponse.class);

        System.out.println("KakaoReadyResponse: " + kakaoReady);

        return kakaoReady;
    }

    public KakaoApproveResponse approveResponse(String pgToken) {

        // 카카오 요청
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("cid", cid);
        parameters.put("tid", kakaoReady.getTid());
        parameters.put("partner_order_id", "가맹점 주문 번호");
        parameters.put("partner_user_id", "가맹점 회원 ID");
        parameters.put("pg_token", pgToken);

        // 파라미터, 헤더
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoApproveResponse approveResponse = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/approve",
                requestEntity,
                KakaoApproveResponse.class);

        return approveResponse;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = " SECRET_KEY " + admin_Key;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/json");

        return httpHeaders;
    }

}

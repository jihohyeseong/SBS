package com.example.bookstore.api;

import com.example.bookstore.dto.*;
import com.example.bookstore.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;

    // 결제 준비
    @PostMapping("/ready")
    public KakaoReadyResponse readyToKakaoPay(@RequestBody List<CartDto> cartDto,
                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        String username = userDetails.getUsername();
        return kakaoPayService.kakaoPayReady(cartDto, username);
    }

    @GetMapping("/success")
    public ResponseEntity afterPayRequest(@RequestParam("pg_token") String pgToken) {

        KakaoApproveResponse kakaoApprove = kakaoPayService.approveResponse(pgToken);

        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
    }
}

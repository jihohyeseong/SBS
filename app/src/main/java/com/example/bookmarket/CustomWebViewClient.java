package com.example.bookmarket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class CustomWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.contains("payment-success")) {  // "payment-successt"가 성공 URL의 일부라고 가정
            // 성공 경우 처리, 성공 액티비티 시작하거나 성공 메시지 표시
            Intent successIntent = new Intent(view.getContext(), PaymentCompleteActivity.class);
            view.getContext().startActivity(successIntent);
            return true;  // 현재 WebView를 벗어나 해당 URL을 직접 처리하려면 true 반환
        } else if (url.contains("payment-cancel")) {
            // 취소 경우 처리
            // 취소 메시지 표시 또는 취소에 따른 조치 수행
            return true;
        } else if (url.contains("payment-failure")) {
            // 실패 경우 처리
            // 오류 메시지 표시 또는 실패에 따른 조치 수행
            return true;
        }

        // 다른 URL의 경우 WebView에 페이지를 로드하게 함
        return super.shouldOverrideUrlLoading(view, url);
    }
}

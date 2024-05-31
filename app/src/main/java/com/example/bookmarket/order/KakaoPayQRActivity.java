package com.example.bookmarket.order;
        import android.os.Bundle;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.ImageView;

        import androidx.appcompat.app.AppCompatActivity;

        import com.bumptech.glide.Glide;
        import com.example.bookmarket.CustomWebViewClient;
        import com.example.bookmarket.R;

public class KakaoPayQRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_pay_qr);

        // WebView 가져오기
        WebView webView = findViewById(R.id.webView);

        // 사용자 정의 WebViewClient 설정
        webView.setWebViewClient(new CustomWebViewClient());

        // 인텐트에서 QR 코드 URL 가져오기
        String qrUrl = getIntent().getStringExtra("qrUrl");

        // QR 코드 URL 로드
        webView.loadUrl(qrUrl);
    }
}
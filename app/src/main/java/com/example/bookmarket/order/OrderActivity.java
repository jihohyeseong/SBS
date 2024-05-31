package com.example.bookmarket.order;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmarket.KakaoPayApiService;
import com.example.bookmarket.MainActivity;
import com.example.bookmarket.PaymentCompleteActivity;
import com.example.bookmarket.R;
import com.example.bookmarket.RetrofitClient;
import com.example.bookmarket.cart.CartActivity;
import com.example.bookmarket.dto.CartDto;
import com.example.bookmarket.dto.KakaoPayReadyResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderActivity extends AppCompatActivity {
    private static final int PAYMENT_REQUEST = 1;  // 결제 요청 코드
    TextView orderTotalObj, textViewDate, textViewName, textViewZipcode, textViewAddress, textViewTotal;

    private KakaoPayApiService kakaoPayApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        kakaoPayApiService = RetrofitClient.getRetrofitInstance().create(KakaoPayApiService.class);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // TextView 초기화
        textViewDate = findViewById(R.id.textViewDate);
        textViewName = findViewById(R.id.textViewName);
        textViewZipcode = findViewById(R.id.textViewZipcode);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewTotal = findViewById(R.id.textViewTotal);

        // 인텐트에서 주문 정보 가져오기
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String date = intent.getStringExtra("date");
            String phone = intent.getStringExtra("phone");
            String zipcode = intent.getStringExtra("zipcode");
            String address = intent.getStringExtra("address");
            String total = intent.getStringExtra("total");

            textViewDate.setText("배송일 : " + date);
            textViewName.setText("성명 : " + name + " (" + phone + ")");
            textViewZipcode.setText("우편번호 : " + zipcode);
            textViewAddress.setText("주소 : " + address);
            textViewTotal.setText(total);
        }

        // RecyclerView 설정
        RecyclerView orderRecyclerView = findViewById(R.id.order_recyclerview);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 카트 아이템 가져오기 (예시)
        List<CartDto> cartItems = MainActivity.cartRepositoryObj.getCartBooks(); // 임시 데이터 리스트
        OrderAdapter orderAdapter = new OrderAdapter(this, cartItems);
        orderRecyclerView.setAdapter(orderAdapter);

        Button orderSubmit = findViewById(R.id.buttonSubmit);
        orderSubmit.setOnClickListener(view -> attemptPayment());

        Button orderCancel = findViewById(R.id.buttonCancel);
        orderCancel.setOnClickListener(view -> cancelOrder());

    }

    private void attemptPayment() {
        List<CartDto> cartItems = MainActivity.cartRepositoryObj.getCartBooks();
        if (cartItems.isEmpty()) {
            Toast.makeText(this, "장바구니가 비어 있습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        KakaoPayApiService kakaoPayApiService = retrofit.create(KakaoPayApiService.class);
        Call<KakaoPayReadyResponse> call = kakaoPayApiService.readyToPay(cartItems);

        call.enqueue(new Callback<KakaoPayReadyResponse>() {
            @Override
            public void onResponse(Call<KakaoPayReadyResponse> call, Response<KakaoPayReadyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 결제 준비 성공, 결제 화면으로 리디렉트
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.body().getNext_redirect_app_url()));
                    startActivityForResult(intent, PAYMENT_REQUEST); // 변경된 부분: 기존 startActivity에서 startActivityForResult로 변경
                } else {
                    // 결제 준비 실패
                    try {
                        Log.e("OrderActivity", "결제 준비 실패: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(OrderActivity.this, "결제 준비 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KakaoPayReadyResponse> call, Throwable t) {
                Toast.makeText(OrderActivity.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYMENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                // 결제 성공 처리
                Intent completeIntent = new Intent(OrderActivity.this, PaymentCompleteActivity.class);
                completeIntent.putExtra("orderInfo", "주문이 완료되었습니다."); // 주문 정보를 넘겨줄 수 있습니다
                startActivity(completeIntent);
                finish();  // 현재 액티비티 종료
            } else {
                // 결제 실패 또는 취소 처리
                Toast.makeText(this, "결제가 취소되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void cancelOrder() {
        new AlertDialog.Builder(this)
                .setTitle("주문 취소")
                .setMessage("주문을 취소하시겠습니까?")
                .setPositiveButton("예", (dialog, which) -> {
                    // 장바구니로 돌아가기
                    Intent intent = new Intent(OrderActivity.this, CartActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();  // 현재 액티비티 종료
                })
                .setNegativeButton("아니오", null)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

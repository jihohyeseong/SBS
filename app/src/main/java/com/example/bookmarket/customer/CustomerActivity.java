package com.example.bookmarket.customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookmarket.R;


public class CustomerActivity extends AppCompatActivity {
    Button infoObj ;
    Button boardObj ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        // ActionBar 가져오기
        ActionBar actionBar = getSupportActionBar();
        // ActionBar가 null인지 확인
        if (actionBar != null) {
            // 홈 버튼을 활성화하고, 클릭 시 뒤로 가기 기능을 수행하도록 설정
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        infoObj = findViewById(R.id.info);
        infoObj.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MarketInfoActivity.class);
            startActivity(intent);
        });

        boardObj =  findViewById(R.id.board);
        boardObj.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
            startActivity(intent);
        });

    }
}
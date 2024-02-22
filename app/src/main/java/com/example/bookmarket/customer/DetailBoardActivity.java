package com.example.bookmarket.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookmarket.R;

public class DetailBoardActivity  extends AppCompatActivity {

    TextView tvTitleObj;
    TextView  tvContentObj;
    TextView   tvUserIDObj;
    TextView  tvDateObj;
    Button button;
    int num = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String userid = intent.getStringExtra("userid");
        String content = intent.getStringExtra("content");
        String date = intent.getStringExtra("date");


        tvTitleObj = findViewById(R.id.textView_Title);
        tvUserIDObj = findViewById(R.id.textView_UserID);
        tvContentObj = findViewById(R.id.textView_Content);
        tvDateObj = findViewById(R.id.textView_Date);
        tvTitleObj.setText(title);
        tvUserIDObj.setText(userid);
        tvContentObj.setText(content);
        tvDateObj.setText(date);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : // 화살표(<-) 클릭 시 이전 액티비티(화면)로 이동하기
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

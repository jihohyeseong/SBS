package com.example.bookmarket.video;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookmarket.R;
public class VideoActivity  extends AppCompatActivity {
    ImageView videoObj01;
    ImageView videoObj02;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 화살표(<-) 표시하기

        videoObj01 = findViewById(R.id.iv_video01);
        videoObj02 = findViewById(R.id.iv_video02);

        videoObj01.setOnClickListener(view -> { // 첫번째 동영상 클릭
            Intent intent = new Intent(getApplicationContext(), VideoPlayActivity.class);
            intent.putExtra("filename", R.raw.video1);
            startActivity(intent);
        });

        videoObj02.setOnClickListener(view -> { // 두번째 동여상 클릭
            Intent intent = new Intent(getApplicationContext(), VideoPlayActivity.class);
            intent.putExtra("filename", R.raw.video2);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // 화살표(<-) 클릭 시 이전 액티비티(화면)로 이동하기
                onBackPressed();
                break;
         }
        return super.onOptionsItemSelected(item);
    }
}

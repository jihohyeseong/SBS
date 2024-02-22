package com.example.bookmarket;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {
    long SPLASH_VIEW_TIME =2000; // 2초 동안 스플래쉬 화면 보이기

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ImageView imageView = findViewById(R.id.imgIntroLogo);
        imageView.setBackgroundResource(R.drawable.intro_ani);

        AnimationDrawable anim = (AnimationDrawable)imageView.getBackground();
        anim.start();

        // 로딩화면을 보여주기 위한 handler 메소드
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);

            startActivity(intent); // 인텐트 명시된 액티비티 이동

            finish(); // 현재 액티비티 종료
        }, SPLASH_VIEW_TIME); //
    }
}

package com.example.bookmarket.video;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bookmarket.R;

public class VideoPlayActivity extends AppCompatActivity {
    VideoView videoView;
    ImageButton btFullscreen;
    boolean isFull = false;
    FrameLayout layout;

    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplay);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        int resName = intent.getIntExtra("filename",0); //인텐트로 동영상 파일 내려받기

        // Toolbar를 레이아웃에서 찾아서 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }


        btFullscreen = findViewById(R.id.bt_fullscreen);
        layout = findViewById(R.id.videoview_frame);
        videoView = findViewById(R.id.videoView);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        Uri uri = Uri.parse("android.resource://"+ getPackageName()+"/"+resName);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

        // 화면 크기 조정 버튼 클릭 이벤트 처리 리스너
        btFullscreen.setOnClickListener(view -> {
            setFullScreen(!isFull);
            if(isFull)
                    btFullscreen.setBackgroundResource(R.drawable.video_resize02);
            else
                    btFullscreen.setBackgroundResource(R.drawable.video_resize01);
        });

    }
    private void setFullScreen(Boolean full) {
        isFull = full;
        ViewGroup.LayoutParams params = layout.getLayoutParams();

        if(isFull) { // 전체화면 크기(가로) 설정
            isFull = true;
            getSupportActionBar().hide();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        );
            } else{ // 축소 화면 크기(세로) 설정
                isFull = false;
                getSupportActionBar().show();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int height = (int)(metrics.density * 250);
            params.height = height;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cart,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.bookmarket.mypage;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.bookmarket.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import com.bumptech.glide.Glide;

public class MyPageActivity extends AppCompatActivity {
    Button orderObj;
    Button  memoObj;

    ImageView loginimg ;
    FloatingActionButton fab_add_photo ;
    File m_imageFile;
    private int REQUEST_TAKE_PHOTO = 100;
    private int REQUEST_IMAGE_CHOOSE =101;
    private ContentResolver contentResolver;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 화살표(<-) 표시하기

        memoObj = findViewById(R.id.memo);
        loginimg = findViewById(R.id.loginimg);
        fab_add_photo = findViewById(R.id.fab_add_photo);
        SharedPreferences pref = getSharedPreferences("prefile",  Context.MODE_PRIVATE);
        String nameStr = pref.getString("filename", null);
        if (nameStr != null && !nameStr.isEmpty()) {
            Glide.with(this).load(nameStr).into(loginimg);
        }

        memoObj.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MemoActivity.class);
            startActivity(intent);
        });

        fab_add_photo.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MyPageActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_choose_app, null);
            builder.setTitle("선택하세요");
            builder.setView(dialogView);
            builder.setNegativeButton("취소", null);
            AlertDialog dialog = builder.show();

            LinearLayout layoutCameraPick = dialogView.findViewById(R.id.layoutCameraPick);
            LinearLayout layoutGalleryPick = dialogView.findViewById(R.id.layoutGalleryPick);

            layoutCameraPick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String packageName = "com.androidbook.bookmarket.mypage.fileprovider";
                    Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), packageName, photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                    m_imageFile = photoFile;
                    dialog.dismiss();
                }
            });
            layoutGalleryPick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_IMAGE_CHOOSE);

                    dialog.dismiss();
                }
            });


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(m_imageFile.getAbsolutePath());
            Glide.with(this).load(Uri.fromFile(m_imageFile)).into(loginimg);

        }
        else  if (requestCode == REQUEST_IMAGE_CHOOSE && resultCode == RESULT_OK) {
            Glide.with(this).load(data.getData()).into(loginimg);
            m_imageFile =new File(data.getData().getPath());
        }
        SharedPreferences pref = getSharedPreferences("prefile",  Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("filename", Uri.fromFile(m_imageFile).toString());
        editor.apply();
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                "JPEG_"+ timeStamp, //_ /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */);

        return imageFile;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
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

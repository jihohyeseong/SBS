package com.example.bookmarket.mypage;



import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.bookmarket.MainActivity;
import com.example.bookmarket.MyApiService;
import com.example.bookmarket.R;

import com.example.bookmarket.RetrofitClient;
import com.example.bookmarket.cart.CartActivity;
import com.example.bookmarket.dto.JoinDto;
import com.example.bookmarket.dto.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyPageActivity extends AppCompatActivity {

    private TextView Username, Email, Phonenum, Address;
    private Button logoutButton, deleteAccountButton, updateUserButton, cartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        initializeViews();
        fetchUserInfo();
        setupButtonListeners();

    }

    private void initializeViews() {
        Username = findViewById(R.id.username);
        Email = findViewById(R.id.email);
        Phonenum = findViewById(R.id.phonenum);
        Address = findViewById(R.id.address);

        logoutButton = findViewById(R.id.logoutButton);
        deleteAccountButton = findViewById(R.id.DeleteAccountButton);
        updateUserButton = findViewById(R.id.UpdateUserInfo);
        cartButton = findViewById(R.id.cartButton);
    }

    private void setupButtonListeners() {
        updateUserButton.setOnClickListener(view -> {
            // 사용자 정보 업데이트 함수 호출
            updateUserInfo();
        });

        deleteAccountButton.setOnClickListener(view -> {
            // 회원 탈퇴 다이얼로그 표시
            //deleteUserAccount();
        });

        logoutButton.setOnClickListener(view -> {
            // 로그아웃 함수 호출
            logout();
        });
        cartButton.setOnClickListener(view -> {
            // 장바구니 액티비티로 이동
            Intent cartIntent = new Intent(MyPageActivity.this, CartActivity.class);
            startActivity(cartIntent);
        });

    }



    private void fetchUserInfo() {
        MyApiService apiService = RetrofitClient.getRetrofitInstance().create(MyApiService.class);
        Call<User> call = apiService.getUserInfo();

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    Username.setText(user.getUsername());
                    Email.setText(user.getEmail());
                    Phonenum.setText(user.getPhoneNum());
                    Address.setText(user.getAddress());
                } else {
                    Toast.makeText(getApplicationContext(), "사용자 정보를 가져오는 데 실패했습니다.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "네트워크 오류 발생", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUserInfo() {
        User user = new User(null, "username", "password", "email@example.com", "010-1234-1234", "address", "ROLE_USER");
        MyApiService apiService = RetrofitClient.getRetrofitInstance().create(MyApiService.class);
        Call<User> call = apiService.updateUserInfo(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MyPageActivity.this, "사용자 정보 업데이트 성공", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyPageActivity.this, "업데이트 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MyPageActivity.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // 모든 사용자 정보 제거
        editor.apply();

        // 사용자 정보 UI 초기화
        clearUserInfoUI();

        MyApiService apiService = RetrofitClient.getRetrofitInstance().create(MyApiService.class);
        Call<Void> call = apiService.logoutUser(); // Implement this method in your API interface
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.e("로그아웃", "로그아웃 완료");
                // 메인 액티비티로 돌아가기
                Intent intent = new Intent(MyPageActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("로그아웃","로그아웃 실패");
            }
        });

    }

    private void clearUserInfoUI() {
        Username.setText("");
        Email.setText("");
        Phonenum.setText("");
        Address.setText("");
    }

    private void deleteUserAccount() {
        MyApiService apiService = RetrofitClient.getRetrofitInstance().create(MyApiService.class);
        Call<Void> call = apiService.deleteUserInfo();

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    logout();
                    Toast.makeText(MyPageActivity.this, "계정 삭제 성공", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyPageActivity.this, "계정 삭제 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MyPageActivity.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        if (isUserLoggedIn()) {
            fetchUserInfo();
        } else {
            clearUserInfoUI();
        }
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", null) != null;
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

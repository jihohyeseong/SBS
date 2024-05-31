package com.example.bookmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookmarket.customer.CustomerActivity;
import com.example.bookmarket.dto.CartRepository;
import com.example.bookmarket.dto.JoinDto;
import com.example.bookmarket.mypage.MyPageActivity;
import com.example.bookmarket.video.VideoActivity;
import com.google.android.material.navigation.NavigationView;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    int index  =0;
    ImageView  menuObj;

    Toolbar toolbar;
    DrawerLayout dLayout;
    NavigationView navigation;

    String loginInfo ="";
    CheckBox cbLoginObj;
    Boolean isLogin = false;
    String mUserId =null ;

    public static CartRepository cartRepositoryObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cartRepositoryObj = CartRepository.getInstance();

        // setSupportActionBar() 메서드가 이미 호출되었는지 확인합니다.
        if (getSupportActionBar() == null) {
            // getSupportActionBar()이 null을 반환하면 아직 액션바가 설정되지 않았으므로 설정합니다.
            setSupportActionBar(toolbar);
        }

        menuObj = findViewById(R.id.imageView);
        toolbar = findViewById(R.id.toolbar); // 툴바 아이디 가져오기
        //setSupportActionBar(toolbar); // 툴바 출력하기

        navigation = findViewById(R.id.navigation); // 네이게이션바 아이디 가져오기
        dLayout =  findViewById(R.id.drawer_layout); // 네비게이션바 레이아웃 가져오기
        toolbar.setNavigationOnClickListener(view -> {
            SharedPreferences pref = getSharedPreferences("user_pref",  MODE_PRIVATE);
            mUserId = pref.getString("username", null);
            if (mUserId!=null){ // 인증 시 메뉴명 변경(로그인 -> 로그아웃)
                navigation.getMenu().findItem(R.id.menu01).setTitle("로그아웃");
            }
            dLayout.openDrawer(Gravity.LEFT);
        });

        navigation.setNavigationItemSelectedListener(this);
        // 네비게이션바 메뉴 항목 선택 이벤트리스너

    }
    public void onBookClick(View view){  // 도서 목록 클릭 시 처리 메소드
        Intent intent = new Intent(this, BooksActivity.class); // 인텐트 설정
        startActivity(intent); // 인텐트 실행
    }
    public void onVideoClick(View view){  // 동영상 클릭 시 처리 메소드
        Intent intent = new Intent(this, VideoActivity.class); // 인텐트 설정
        startActivity(intent); // 인텐트 실행
    }
    public void onCustomerClick(View view){  //고객센터 클릭 시 처리 메소드
        Intent intent = new Intent(this,CustomerActivity.class);
        startActivity(intent);
    }
    public void onMyPageClick(View view){  // 마이페이지 클릭 시 처리 메소드
        Intent intent = new Intent(this,MyPageActivity.class);
        startActivity(intent);
    }
    public void onImageClick(View view){  // 커버이미지 클릭 시 처리 메소드
        ImageView menuObj = (ImageView)findViewById(R.id.imageView);
        if(index % 2 == 0)menuObj.setImageResource(R.drawable.cover02);
        else menuObj.setImageResource(R.drawable.cover01);
        index++;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu01:
                if (item.getTitle().equals("로그인")) {
                    showLoginDialog(item);
                } else {
                    showLogoutConfirmationDialog(item);
                }
                break;
            case R.id.menu02: //나의정보
                Intent mypageIntent = new Intent(MainActivity.this, MyPageActivity.class);
                startActivity(mypageIntent);
                break;
            case R.id.menu03: //도서관리
                Intent intent = new Intent(MainActivity.this, subActivity.class);
                startActivity(intent);
                break;
            case R.id.menu04: //도서랭킹
                Intent rankingIntent = new Intent(MainActivity.this, RankingActivity.class);
                startActivity(rankingIntent);
                break;
            case R.id.menu05: //신간도서
                Intent NewBookIntent = new Intent(MainActivity.this, NewBookActivity.class);
                startActivity(NewBookIntent);
                break;
            case R.id.menu06: //회원가입
                showJoinDialog();
                break;
        }
        dLayout.closeDrawers();
        return false;
    }

    private void showLoginDialog(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_login, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create(); // AlertDialog 생성

        EditText ID = dialogView.findViewById(R.id.userID);
        EditText PW = dialogView.findViewById(R.id.userPW);
        Button loginBtn = dialogView.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(view -> {
            String username = ID.getText().toString();
            String password = PW.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "아이디와 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(username, password);
                Log.e("username",username);
                Log.e("password",password);
                dialog.dismiss();
            }
        });
        dialog.show(); // AlertDialog 표시
    }

    private void loginUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        MyApiService apiService = RetrofitClient.getRetrofitInstance().create(MyApiService.class);
        Call<LoginResponse> call = apiService.login(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SharedPreferences pref = getSharedPreferences("user_pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username", response.body().getUsername());
                    editor.putString("sessionId", response.body().getSessionId());
                    editor.commit();
                    //item.setTitle("로그아웃");

                    Toast.makeText(MainActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "로그인 실패!", Toast.LENGTH_SHORT).show();
                    Log.e("로그인", "실패");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // 네트워크 오류 등의 이유로 요청 실패
                //Toast.makeText(MainActivity.this, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showLogoutConfirmationDialog(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("로그아웃")
                .setMessage("로그아웃하시겠습니까?")
                .setPositiveButton("예", (dialog, which) -> {
                    SharedPreferences pref = getSharedPreferences("user_pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.remove("username");
                    editor.apply();

                    item.setTitle("로그인");
                    Toast.makeText(MainActivity.this, "로그아웃 완료!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("아니요", null)
                .show();
    }

    private void showJoinDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_join, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.show();

        Button joinBtn = dialogView.findViewById(R.id.joinBtn);
        joinBtn.setOnClickListener(view -> {
            EditText UserName = dialogView.findViewById(R.id.userName);
            EditText PW = dialogView.findViewById(R.id.userPW);
            EditText Email = dialogView.findViewById(R.id.userEmail);
            EditText PhoneNum = dialogView.findViewById(R.id.userPhoneNum);
            EditText Address = dialogView.findViewById(R.id.userAddress);

            // 회원가입 정보를 생성합니다.
            JoinDto joinDto = new JoinDto(
                    UserName.getText().toString(),
                    PW.getText().toString(),
                    Email.getText().toString(),
                    PhoneNum.getText().toString(),
                    Address.getText().toString()
            );

            // Retrofit을 사용하여 스프링 서버로 회원가입 정보를 전송합니다.
            MyApiService apiService = RetrofitClient.getRetrofitInstance().create(MyApiService.class);
            Call<JoinResponse> call = apiService.register(joinDto);
            call.enqueue(new Callback<JoinResponse>() {
                @Override
                public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                    if (response.isSuccessful()) {
                        // 회원가입 성공 처리
                        JoinResponse joinResponse = response.body();
                        if (joinResponse != null && joinResponse.getCode() == 1) {
                            Toast.makeText(MainActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // 서버 응답 실패 처리
                        //Toast.makeText(MainActivity.this, "서버 응답 실패", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JoinResponse> call, Throwable t) {
                    // 네트워크 오류 처리
                    Toast.makeText(MainActivity.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.dismiss();
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isLogin) {
            SharedPreferences pref = getSharedPreferences("user_pref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.remove("username");
            editor.apply();
        }
    }
}
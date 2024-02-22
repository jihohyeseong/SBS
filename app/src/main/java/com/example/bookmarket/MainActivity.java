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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookmarket.customer.CustomerActivity;
import com.example.bookmarket.model.CartRepository;
import com.example.bookmarket.mypage.MyPageActivity;
import com.example.bookmarket.video.VideoActivity;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    int index  =0;
    ImageView  menuObj;

    Toolbar toolbar;
    DrawerLayout dLayout;
    NavigationView navigation;

    String loginInfo ="";
    CheckBox cbLoginObj;
    Boolean isLogin = true;
    String mUserId =null ;

    public static CartRepository cartRepositoryObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cartRepositoryObj = new CartRepository();

        menuObj = findViewById(R.id.imageView);
        toolbar = findViewById(R.id.toolbar); // 툴바 아이디 가져오기
        setSupportActionBar(toolbar); // 툴바 출력하기
        navigation = findViewById(R.id.navigation); // 네이게이션바 아이디 가져오기
        dLayout =  findViewById(R.id.drawer_layout); // 네비게이션바 레이아웃 가져오기
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("prefile",  MODE_PRIVATE);
                mUserId = pref.getString("id", null);
                if (mUserId!=null){ // 인증 시 메뉴명 변경(로그인 -> 로그아웃)
                    navigation.getMenu().findItem(R.id.menu01).setTitle("로그아웃");
                }
                dLayout.openDrawer(Gravity.LEFT);
            }
        });

        navigation.setNavigationItemSelectedListener(this);
        // 네비게이션바 메뉴 항목 선택 이벤트리스너

    }
    public void onBookClick(View view){  // 도서 목록 클릭 시 처리 메소드
        //Toast.makeText(getApplicationContext(),"도서목록 버튼이 클릭되었습니다",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, BooksActivity.class); // 인텐트 설정
        startActivity(intent); // 인텐트 실행
    }
    public void onVideoClick(View view){  // 동영상 클릭 시 처리 메소드
        //Toast.makeText(getApplicationContext(),"동영상강좌 버튼이 클릭되었습니다",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, VideoActivity.class); // 인텐트 설정
        startActivity(intent); // 인텐트 실행
    }
    public void onCustomerClick(View view){  //고객센터 클릭 시 처리 메소드
        //Toast.makeText(getApplicationContext(),"고객센터 버튼이 클릭되었습니다",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,CustomerActivity.class);
        startActivity(intent);
    }
    public void onMyPageClick(View view){  // 마이페이지 클릭 시 처리 메소드
        //Toast.makeText(getApplicationContext(),"마이페이지 버튼이 클릭되었습니다",Toast.LENGTH_LONG).show();
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
                // Toast.makeText(this, " 메뉴1 : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                if (item.getTitle().equals("로그인")) { // 메뉴명이 '로그인'일 때
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_login, null);
                    builder.setView(dialogView);
                    AlertDialog dialog = builder.show();

                    CheckBox cbLoginObj = dialogView.findViewById(R.id.checkBox);
                    cbLoginObj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if ( b) isLogin = true;
                            else isLogin = false;
                        }
                    });


                    Button loginBtn = dialogView.findViewById(R.id.loginBtn);
                    // 네비게이션바의 로그인 매뉴인 메뉴1 클릭 시 로그인 대화상자 불러오기
                    loginBtn.setOnClickListener(view -> {
                        TextView tvID = dialogView.findViewById(R.id.userID);
                        TextView tvPW = dialogView.findViewById(R.id.userPW);
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "아이디: " + tvID.getText() + "비밀번호: " + tvPW.getText(), Toast.LENGTH_LONG).show();
                        loginCheckMember(tvID,tvPW, item);
                    });
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("AlertDialog"); // 제목 표시
                    builder.setMessage("로그아웃하시겠습니까?"); // 메시지 내용 표시
                    DialogInterface.OnClickListener listener  = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            item.setTitle("로그인");
                            SharedPreferences pref =getSharedPreferences("prefile", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.remove("id");
                            editor.apply();
                        }
                    };


                    builder.setPositiveButton( "예", listener);
                    builder.setNegativeButton("아니요", null); // '아니요' 버튼 표시
                    builder.show();
                }
                break;
            case R.id.menu02:
                Toast.makeText(this, " 메뉴2 : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu03:
                Toast.makeText(this, " 메뉴3 : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu04 :
                AlertDialog.Builder builder_join= new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater_join = getLayoutInflater();
                View dialogView_join = inflater_join.inflate(R.layout.dialog_join, null);
                builder_join.setView(dialogView_join);
                AlertDialog dialog_join = builder_join.show();
                Button joinBtn = dialogView_join.findViewById(R.id.joinBtn);
                joinBtn.setOnClickListener(view -> {
                    EditText edtID = dialogView_join.findViewById(R.id.userID);
                    EditText edtPW = dialogView_join.findViewById(R.id.userPW);
                    EditText edtName = dialogView_join.findViewById(R.id.userName);
                    EditText edtPhone = dialogView_join.findViewById(R.id.userPhone);
                    dialog_join.dismiss();
                    insertMember(edtID,edtPW, edtName, edtPhone);
                });
                break;
        }
        dLayout.closeDrawers();
        return false;
    }

    @Override
    public void onBackPressed() {
        if (dLayout.isDrawerOpen(Gravity.LEFT)) {
            dLayout.closeDrawers();
        } else {

            super.onBackPressed();
        }
    }
    public void  insertMember(EditText edtID, EditText edtPW, EditText edtName, EditText edtPhone){
        String url = "http://androidbook.dothome.co.kr/insertMember.php";

        RequestBody requestBody  = new FormBody.Builder() // 전송 데이터의 설정
                .add("id", edtID.getText().toString())
                .add("passwd", edtPW.getText().toString())
                .add("name", edtName.getText().toString())
                .add("phone", edtPhone.getText().toString())
                .build();
        Request request = new Request.Builder() //요청 설정
                .url(url)
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    // 응답 실패
                    //  Log.i("tag", "응답실패");
                } else {
                    // 응답 성공
                    // Log.i("tag", "응답 성공");
                    final String body = response.body().string();
                    // 서브 스레드 Ui 변경 할 경우 에러
                    // 메인스레드 Ui 설정
                    runOnUiThread(() -> {
                        try {

                            if (!response.isSuccessful())
                                Toast.makeText(getApplicationContext(), "네트워크 문제 발생", Toast.LENGTH_SHORT).show();
                            else if (body.equals("0"))
                                Toast.makeText(getApplicationContext(), "삽입 실패", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplicationContext(), "삽입 성공", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }
    public void loginCheckMember( TextView tvID, TextView tvPW,MenuItem item ){
        String url = "http://androidbook.dothome.co.kr/selectMember.php";

        FormBody requestBody = new FormBody.Builder()
                .add("id", tvID.getText().toString())
                .add("passwd", tvPW.getText().toString())
                .build();



        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    // 응답 실패
                    //  Log.i("tag", "응답실패");
                } else {
                    // 응답 성공
                    // Log.i("tag", "응답 성공");
                    final String body = response.body().string();
                    // 서브 스레드 Ui 변경 할 경우 에러
                    // 메인스레드 Ui 설정
                    runOnUiThread(() -> {
                        try {

                            if (!response.isSuccessful())
                                Toast.makeText(getApplicationContext(), "네트워크 문제 발생", Toast.LENGTH_SHORT).show();
                            else if (body.equals("0"))
                                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                            else if (body.equals("1")) {
                                item.setTitle("로그아웃");
                                SharedPreferences pref = getSharedPreferences(
                                        "prefile", Context.MODE_PRIVATE
                                );
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("id", tvID.getText().toString());
                                editor.putString("passwd", tvPW.getText().toString());
                                editor.apply();
                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isLogin) { // '로그인 정보 기억하기'를 체크하지 않으면 로그인 정보 삭제
            SharedPreferences pref = getSharedPreferences("prefile", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.remove("id");
            editor.apply();
        }
    }
}

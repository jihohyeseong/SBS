package com.example.bookmarket.customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookmarket.R;

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

public class WriteBoardActivity extends AppCompatActivity {

    EditText edtTitleObj;
    EditText  edtContentObj;
    Button btInsertObj;
    Button  btCancelObj;
    String  loginInfo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        edtTitleObj = findViewById(R.id.editTextTitle);
        edtContentObj = findViewById(R.id.editTextContent);
        btInsertObj = findViewById(R.id.buttonInsert);
        btCancelObj = findViewById(R.id.buttonCancel);

        btInsertObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref  = getSharedPreferences("prefile", Context.MODE_PRIVATE);
                String useId = pref.getString("id", null);
                writeBoard(useId);
            }
        });

        btInsertObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    void  writeBoard( String useId){
        String url = "http://androidbook.dothome.co.kr/insertBoard.php";

        RequestBody requestBody = new FormBody.Builder()
                .add("id", useId.toString())
                .add("title", edtTitleObj.getText().toString())
                .add("content", edtContentObj.getText().toString())
                .build();

        // OkHttp 요청 만들기
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {

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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });

        Intent intent = new Intent(this, BoardActivity.class);
        startActivity(intent);
        finish();
    }
}

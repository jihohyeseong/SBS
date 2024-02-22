package com.example.bookmarket.customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookmarket.R;
import com.example.bookmarket.cart.CartAdapter;
import com.example.bookmarket.mypage.MemoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BoardActivity  extends AppCompatActivity {

    Button btWriteObj;
    ListView listview;
    MyAdapter  adapter;
    ArrayList<MyItem> itemList = new ArrayList<MyItem>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 화살표(<-) 표시하기
       btWriteObj = findViewById(R.id.writeButton);
        btWriteObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("prefile", Context.MODE_PRIVATE);
                String useId = pref.getString("id", null);
                if (useId==null) Toast.makeText(getApplicationContext(), "로그인해 주세요" , Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(getApplicationContext(), WriteBoardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        listview =findViewById(R.id.listView);
        adapter = new MyAdapter(itemList);
        listview.setAdapter(adapter);
        itemListJson();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyItem itemValue = (MyItem) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), DetailBoardActivity.class);
                intent.putExtra("title", itemValue.title);
                intent.putExtra("userid", itemValue.id);
                intent.putExtra("content", itemValue.content);
                intent.putExtra("date", itemValue.date);
                startActivity(intent);
            }
        });



    }

    void itemListJson() { // 웹 서버 연동

        String url = "http://androidbook.dothome.co.kr/selectBoard.php";

        Request request = new Request.Builder()
                .url(url)
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                if (!response.isSuccessful())
                                    Toast.makeText(getApplicationContext(), "네트워크 문제 발생", Toast.LENGTH_SHORT).show();
                                else if (body.equals("0"))
                                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                                else {
                                    JSONObject jsonObject = new JSONObject(body);
                                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                                    for (int i = 0; i < jsonArray.length() - 1; i++) {
                                        JSONObject iObject = jsonArray.getJSONObject(i);
                                        int no = iObject.getInt("no");
                                        String id = iObject.getString("id");
                                        String title = iObject.getString("title");
                                        String content = iObject.getString("content");
                                        String date = iObject.getString("date");
                                        itemList.add(new MyItem(no, id, title, content, date));
                                    }
                                    adapter = new MyAdapter(itemList);
                                    listview.setAdapter(adapter);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
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

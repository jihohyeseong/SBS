package com.example.bookmarket;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookmarket.model.Book;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class subActivity extends AppCompatActivity {

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 권한 확인 로직
        if (!isAdmin()) {
            Toast.makeText(this, "접근 권한이 없습니다.", Toast.LENGTH_LONG).show();
            finish(); // 액티비티 종료, 관리자가 아니면 액티비티 접근을 차단
            return; // 이후 로직 실행 방지
        }

        setContentView(R.layout.retrofit);
        textViewResult = findViewById(R.id.text_view_result);

        fetchBooks(); // 책 목록을 불러오는 메소드
    }

    private boolean isAdmin() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("role", "ROLE_USER");
        return role.equals("admin");
    }

    private void fetchBooks() {
        MyApiService apiService = RetrofitClient.getRetrofitInstance().create(MyApiService.class);
        Call<List<Book>> call = apiService.index();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayBooks(response.body());
                } else {
                    textViewResult.setText("도서 목록을 불러오지 못했습니다.");
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                textViewResult.setText("네트워크 오류: " + t.getMessage());
            }
        });
    }

    private void displayBooks(List<Book> books) {
        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append("작가: ").append(book.getAuthor()).append("\n");
            sb.append("출판사: ").append(book.getPublisher()).append("\n");
            sb.append("가격: ").append(book.getPrice()).append("\n");
            sb.append("설명: ").append(book.getDescription()).append("\n\n");
            sb.append("카테고리: ").append(book.getCategory()).append("\n");
            sb.append("도서명: ").append(book.getBookName()).append("\n");
            sb.append("출판날짜: ").append(book.getRelease_date()).append("\n");
            sb.append("책 이미지: ").append(book.getImage_url()).append("\n");
            sb.append("누적구매량: ").append(book.getUnits_in_stock()).append("\n\n");
            sb.append("재고: ").append(book.getUnits_in_stock()).append("\n\n");
        }
        textViewResult.setText(sb.toString());
    }
}

package com.example.bookmarket;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmarket.model.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RankingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadRankingBooks();
    }

    private void loadRankingBooks() {
        // RetrofitClient에서 Retrofit 인스턴스를 가져와서 MyApiService를 생성합니다.
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        MyApiService apiService = retrofit.create(MyApiService.class);

        Call<List<Book>> call = apiService.getBookRankings();

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    List<Book> rankingBooks = response.body();
                    bookAdapter = new BookAdapter(RankingActivity.this, rankingBooks);
                    recyclerView.setAdapter(bookAdapter);
                } else {
                    Toast.makeText(RankingActivity.this, "도서 랭킹 정보를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(RankingActivity.this, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

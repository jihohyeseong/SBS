package com.example.bookmarket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import com.example.bookmarket.dto.BookDto;
import com.example.bookmarket.model.Book;
import com.example.bookmarket.mypage.MyPageActivity;
import com.example.bookmarket.video.VideoActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BooksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList = new ArrayList<>();

    private BookDto dto;

    // 버튼 객체 선언
    private Button buttonAddBook;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        recyclerView = findViewById(R.id.recyclerViewBooks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrofit 객체 생성
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.79.46.118:8080/") // API의 기본 URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // MyApiService 인터페이스 구현체 생성
        MyApiService apiService = retrofit.create(MyApiService.class);

        List<Book> bookList = new ArrayList<>();

        // 도서 목록 요청
        Call<List<Book>> call = apiService.index();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    List<Book>books  = response.body();
                    bookList.addAll(books);  // 먼저 데이터를 리스트에 추가
                    // 수정된 코드
                    bookAdapter = new BookAdapter(BooksActivity.this, bookList);
                    recyclerView.setAdapter(bookAdapter);
                    Log.e("POST", "성공");
                } else {
                    // API 호출은 성공했지만 데이터가 올바르지 않은 경우 처리
                    Log.e("POST", "데이터가 올바르지 않음");
                }
            }
            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                // API 호출 실패 시 처리
                Log.e("API", "호출 실패");
            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar); // toolbar 아이디 확인 필요
        setSupportActionBar(toolbar);

        // ActionBar 가져오기
        ActionBar actionBar = getSupportActionBar();

        // ActionBar가 null인지 확인
        if (actionBar != null) {
            // 홈 버튼을 활성화하고, 클릭 시 뒤로 가기 기능을 수행하도록 설정
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(BooksActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(childView);
                    Book selectedBook = bookList.get(position);
                    // 선택된 도서의 정보를 다음 화면으로 전달하는 메소드 호출
                    showBookDetail(selectedBook);
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });



        buttonAddBook = findViewById(R.id.buttonAdd);
        // 버튼 클릭 이벤트 처리
        buttonAddBook.setOnClickListener(v -> {
            // 도서 등록 다이얼로그 표시
            showAddBookDialog();
        });
    }

    // 도서 등록
    private void showAddBookDialog() {
        // 여기에 도서 등록 다이얼로그를 표시하는 코드 작성
        BookDialogFragment dialog = new BookDialogFragment();
        dialog.show(getSupportFragmentManager(), "AddBookDialogFragment");
    }

    // 도서 수정
    private void showEditBookDialog(Book book) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_book, null);

        EditText BookAuthor = view.findViewById(R.id.author);
        EditText BookPublisher = view.findViewById(R.id.publisher);
        EditText BookPrice = view.findViewById(R.id.price);
        EditText BookCategory = view.findViewById(R.id.category);
        EditText BookName = view.findViewById(R.id.book_name);
        EditText BookDescription = view.findViewById(R.id.description);
        EditText BookBuyNum = view.findViewById(R.id.buy_num);
        EditText BookReleaseDate = view.findViewById(R.id.release_date);
        EditText BookImageUrl = view.findViewById(R.id.image_url);
        EditText BookUnitsInStock = view.findViewById(R.id.units_in_stock);


        // 수정 다이얼로그에 기존 도서 정보를 표시
        BookAuthor.setText(book.getAuthor());
        BookPublisher.setText(book.getPublisher());
        BookPrice.setText(String.valueOf(book.getPrice()));
        BookCategory.setText(book.getCategory());
        BookName.setText(book.getBookName());
        BookDescription.setText(book.getDescription());
        BookBuyNum.setText(String.valueOf(book.getBuy_num()));
        BookReleaseDate.setText(book.getRelease_date());
        BookImageUrl.setText(book.getImage_url());
        BookUnitsInStock.setText(String.valueOf(book.getUnits_in_stock()));


        builder.setView(view)
                .setTitle("도서 수정")
                .setPositiveButton("저장", (dialog, which) -> {
                    // 수정된 도서 정보를 가져와서 서버로 업데이트하는 메소드 호출
                    String updatedAuthor = BookAuthor.getText().toString().trim();
                    String updatedPublisher = BookPublisher.getText().toString().trim();
                    Long updatedPrice = Long.valueOf(BookPrice.getText().toString().trim());
                    String updatedCategory = BookCategory.getText().toString().trim();
                    String updatedBookName = BookName.getText().toString().trim();
                    String updatedDescription = BookDescription.getText().toString().trim();
                    Long updatedBuyNum = Long.valueOf(BookBuyNum.getText().toString().trim());
                    String updatedReleaseDate = BookReleaseDate.getText().toString().trim();
                    String updatedImageUrl = BookImageUrl.getText().toString().trim();
                    Long updatedUnitsInStock = Long.valueOf(BookUnitsInStock.getText().toString().trim());

                    // 나머지 수정된 도서 정보도 가져와서 변수에 저장하세요...
                    updateBook(dto.getId(), updatedAuthor, updatedPublisher, updatedPrice, updatedDescription, updatedBookName, updatedBuyNum, updatedCategory, updatedReleaseDate, updatedImageUrl, updatedUnitsInStock);
                })
                .setNegativeButton("취소", (dialog, which) -> {
                    dialog.dismiss();
                });

        builder.create().show();
    }

    // 도서 삭제 다이얼로그를 표시하는 메소드
    private void showDeleteBookDialog(Book book) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("도서 삭제")
                .setMessage(book.getBookName() + "을(를) 삭제하시겠습니까?")
                .setPositiveButton("삭제", (dialog, which) -> {
                    deleteBook(book.getId());
                })
                .setNegativeButton("취소", (dialog, which) -> {
                    dialog.dismiss();
                });

        builder.create().show();
    }

    // 도서를 서버에서 업데이트하는 메소드
    private void updateBook(Long id, String author, String publisher, Long price, String description, String bookname, Long buy_num, String category, String releasedate, String imageurl, Long unitsinstock) {
        // Retrofit을 사용하여 서버에 도서 정보를 업데이트하는 요청 생성
        MyApiService apiService = RetrofitClient.getRetrofitInstance().create(MyApiService.class);

        // BookDto 객체 생성 및 설정
        BookDto bookDto = new BookDto(id, author, publisher, price, description, bookname, buy_num, category, releasedate, imageurl, unitsinstock);

        // 서버에 업데이트 요청
        Call<Book> call = apiService.updateBook(id, bookDto);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(BooksActivity.this, "도서가 성공적으로 업데이트되었습니다.", Toast.LENGTH_SHORT).show();
                    // 업데이트가 성공하면 도서 목록을 다시 요청하여 업데이트된 목록으로 갱신
                } else {
                    Toast.makeText(BooksActivity.this, "도서 업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(BooksActivity.this, "도서 업데이트 요청 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 도서를 서버에서 삭제하는 메소드
    private void deleteBook(Long id) {
        // Retrofit을 사용하여 서버에서 도서를 삭제하는 요청 생성
        MyApiService apiService = RetrofitClient.getRetrofitInstance().create(MyApiService.class);
        Call<Void> call = apiService.deleteBook(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(BooksActivity.this, "도서가 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    // 삭제가 성공하면 도서 목록을 다시 요청하여 갱신
                } else {
                    Toast.makeText(BooksActivity.this, "도서 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(BooksActivity.this, "도서 삭제 요청 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBookDetail(Book selectedBook) {
        Intent intent = new Intent(this, BookActivity.class);
        intent.putExtra("selectedBook", selectedBook);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_books, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("검색어(도서명)을 입력해주세요.");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                bookAdapter.filterBooks(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                bookAdapter.filterBooks(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: // 화살표(<-) 클릭 시 이전 액티비티(화면)로 이동하기
                onBackPressed();
                break;
            case R.id.menu_home:
                Intent homeintent = new Intent(this, MainActivity.class);
                startActivity(homeintent);
                break;
            case R.id.main_video:
                Intent videointent = new Intent(this, VideoActivity.class);
                startActivity(videointent);
                break;
            case R.id.menu_mypage:
                Intent mypageintent = new Intent(this, MyPageActivity.class);
                startActivity(mypageintent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


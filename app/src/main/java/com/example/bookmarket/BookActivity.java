package com.example.bookmarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.bookmarket.cart.CartActivity;
import com.example.bookmarket.dto.BookDto;
import com.example.bookmarket.dto.CartDto;
import com.example.bookmarket.dto.CartRepository;
import com.example.bookmarket.dto.CommentDto;
import com.example.bookmarket.model.Book;
import com.example.bookmarket.model.BookViewModel;

import java.util.List;

import kotlinx.coroutines.Dispatchers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BookActivity extends AppCompatActivity {
    private BookViewModel bookViewModel;
    private EditText commentEditText;
    private RecyclerView commentRecyclerView;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        commentRecyclerView = findViewById(R.id.commentRecyclerView);
        commentAdapter = new CommentAdapter();
        commentRecyclerView.setAdapter(commentAdapter);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        commentEditText = findViewById(R.id.commentText);

        Button addToCartButton = findViewById(R.id.addToCart);
        addToCartButton.setOnClickListener(view -> addToCart());

        Book selectedBook = getIntent().getParcelableExtra("selectedBook");
        if (selectedBook != null) {
            bookViewModel.setBook(selectedBook);
            displayBookDetails(selectedBook);
            loadComments(selectedBook.getId());
        } else {
            Toast.makeText(this, "도서 정보를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
        }

        Button addCommentButton = findViewById(R.id.submitButton);
        addCommentButton.setOnClickListener(view -> addComment());
    }

    private void addToCart() {
        Book selectedBook = bookViewModel.getBook().getValue();
        if (selectedBook != null) {
            CartDto cartItem = new CartDto(selectedBook.getId(), selectedBook.getBookName(), selectedBook.getPrice(), 1L, selectedBook.imageurl);
            CartRepository.getInstance().addCartItems(cartItem);
            Toast.makeText(this, "장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, CartActivity.class));
        } else {
            Toast.makeText(this, "도서 정보를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayBookDetails(Book book) {
        //TextView bookId = findViewById(R.id.bookid);
        TextView bookAuthor = findViewById(R.id.author);
        TextView bookName = findViewById(R.id.book_name);
        TextView bookPrice = findViewById(R.id.price);
        TextView bookReleaseDate = findViewById(R.id.release_date);
        TextView bookPublisher = findViewById(R.id.publisher);
        TextView bookDescription = findViewById(R.id.description);
        TextView bookCategory = findViewById(R.id.category);

        //bookId.setText(String.valueOf(book.getId()));
        bookAuthor.setText(book.getAuthor());
        bookName.setText(book.getBookName());
        bookPrice.setText(String.valueOf(book.getPrice()));
        bookReleaseDate.setText(book.getRelease_date());
        bookPublisher.setText(book.getPublisher());
        bookDescription.setText(book.getDescription());
        bookCategory.setText(book.getCategory());

        ImageView bookImage = findViewById(R.id.bookImageView);
        Glide.with(this).load(book.getImage_url()).placeholder(R.drawable.placeholder_image).error(R.drawable.baseline_error_24).centerInside().into(bookImage);
    }

    private void addComment() {
        Book selectedBook = bookViewModel.getBook().getValue();
        if (selectedBook != null && !commentEditText.getText().toString().isEmpty()) {
            String commentText = commentEditText.getText().toString();
            String username = getUsernameFromPreferences();
            CommentDto commentDto = new CommentDto(selectedBook.getId(), username, commentText, null); // 여기서 'null'은 bookname을 위한 임시 값입니다.

            postComment(selectedBook.getId(), commentDto);
        } else {
            Toast.makeText(this, "댓글을 입력하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void postComment(Long bookId, CommentDto commentDto) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://52.79.46.118:8080/").addConverterFactory(GsonConverterFactory.create()).build();
        MyApiService apiService = retrofit.create(MyApiService.class);
        Call<CommentDto> call = apiService.createComment(bookId, commentDto);
        call.enqueue(new Callback<CommentDto>() {
            @Override
            public void onResponse(Call<CommentDto> call, Response<CommentDto> response) {
                if (response.isSuccessful()) {
                    CommentDto newComment = response.body();
                    commentAdapter.addComment(newComment);
                    Toast.makeText(BookActivity.this, "댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BookActivity.this, "댓글을 등록하는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommentDto> call, Throwable t) {
                Toast.makeText(BookActivity.this, "댓글을 등록하는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadComments(Long bookId) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://52.79.46.118:8080/").addConverterFactory(GsonConverterFactory.create()).build();
        MyApiService apiService = retrofit.create(MyApiService.class);
        Call<List<CommentDto>> call = apiService.getCommentsForBook(bookId);
        call.enqueue(new Callback<List<CommentDto>>() {
            @Override
            public void onResponse(Call<List<CommentDto>> call, Response<List<CommentDto>> response) {
                if (response.isSuccessful()) {
                    List<CommentDto> comments = response.body();
                    commentAdapter.setComments(comments);
                } else {
                    Toast.makeText(BookActivity.this, "댓글을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
             public void onFailure(Call<List<CommentDto>> call, Throwable t) {
                Toast.makeText(BookActivity.this, "네트워크 오류 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getUsernameFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("username", "ROLE_USER");
    }
}
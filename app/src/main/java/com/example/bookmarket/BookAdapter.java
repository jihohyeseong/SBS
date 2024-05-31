package com.example.bookmarket;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import com.example.bookmarket.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books;
    private Context context; // Context 추가
    private List<Book> filteredBooks; // 필터링된 도서 목록을 저장할 변수

    public BookAdapter(Context context, List<Book> books) {
        this.books = books;
        this.context = context;
        this.filteredBooks = new ArrayList<>(books);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book currentBook = filteredBooks.get(position);

        // Glide를 사용하여 이미지 로드 및 설정
        Glide.with(holder.itemView.getContext())
                .load(currentBook.getImage_url()) // 이미지 URL 가져오기
                .placeholder(R.drawable.placeholder_image) // 이미지 로딩 중에 표시될 placeholder 설정
                .error(R.drawable.baseline_error_24) // 이미지 로드 실패 시 표시될 에러 이미지 설정
                .centerInside()
                .into(holder.bookImageView); // 이미지를 표시할 ImageView 지정

        holder.book_name.setText("제목: " + currentBook.getBookName());
        holder.price.setText("가격: " + currentBook.getPrice());
        holder.publisher.setText("출판사: " + currentBook.getPublisher());
        holder.description.setText("설명:" + currentBook.getDescription());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookActivity.class);
            intent.putExtra("selectedBook", currentBook);
            context.startActivity(intent);
        });

    }

    // 필터링된 도서 목록을 업데이트하는 메서드
    public void filterBooks(String query) {
        filteredBooks.clear();
        if (query.isEmpty()) {
            filteredBooks.addAll(books);
        } else {
            for (Book book : books) {
                if (book.getBookName().toLowerCase().contains(query.toLowerCase())) {
                    filteredBooks.add(book);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filteredBooks.size(); // 필터링된 목록의 크기를 반환
    }


    public class BookViewHolder extends RecyclerView.ViewHolder {
        public ImageView bookImageView;
        public TextView publisher;
        public TextView price;
        public TextView category;
        public TextView description;
        public TextView book_name;
        public TextView release_date;
        public TextView image_url;
        public TextView units_in_stock;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImageView = itemView.findViewById(R.id.bookImageView);
            book_name = itemView.findViewById(R.id.book_name);
            price = itemView.findViewById(R.id.price);
            publisher = itemView.findViewById(R.id.publisher);
            description = itemView.findViewById(R.id.description);
            category = itemView.findViewById(R.id.category);
            release_date = itemView.findViewById(R.id.release_date);
            image_url = itemView.findViewById(R.id.image_url); // 이 부분이 추가되어야 합니다.
            units_in_stock = itemView.findViewById(R.id.units_in_stock);
        }
    }
}
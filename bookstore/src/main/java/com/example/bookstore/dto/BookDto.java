package com.example.bookstore.dto;

import com.example.bookstore.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class BookDto {
    private Long id;
    private String bookName; // 책 이름
    private String author; // 작가
    private String publisher; // 출판사
    private Long price; // 가격
    private String category; // 카테고리
    private String releaseDate; // 출판날짜
    private String imageUrl; // 책 이미지
    private Long unitsInStock; // 재고

    public Book toEntity() {
        return new Book(id,bookName,author,publisher,price,category,releaseDate,imageUrl,unitsInStock);
    }
}

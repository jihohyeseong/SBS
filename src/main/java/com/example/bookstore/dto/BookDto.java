package com.example.bookstore.dto;

import com.example.bookstore.entity.Book;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookDto {
    private Long id;            // 도서 id(primary key)
    private String bookname;    // 도서 이름(bookName)
    private Long price;       // 도서 가격
    private String author;      // 도서 작가,지은이
    private String description; // 도서 상세 내용
    private String publisher;   // 도서 출판사
    private String category;    // 도서 분류
    private Long unitsinstock;  // 도서 재고 수량
    private String releasedate; // 도서 출간 날짜(년/월)
    private String imageurl;    // 도서 사진(url)
    private Long buyNum; // 누적구매량
    private Boolean isNew; // 신간도서 구분
    private Boolean hasEbook; // e북 포함여부

    public Book toEntity() {
        return new Book(id, bookname, price, author, description, publisher, category, unitsinstock, releasedate, imageurl, buyNum, isNew, false);
    }
}

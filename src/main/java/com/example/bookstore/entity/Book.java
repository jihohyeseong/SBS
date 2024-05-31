package com.example.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String bookname;    // 도서 이름(bookName)

    @Column
    private Long price;       // 도서 가격

    @Column
    private String author;      // 도서 작가,지은이

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description; // 도서 상세 내용

    @Column
    private String publisher;   // 도서 출판사

    @Column
    private String category;    // 도서 분류

    @Column
    private Long unitsinstock;  // 도서 재고 수량

    @Column
    private String releasedate; // 도서 출간 날짜(년/월)

    @Column
    private String imageurl;    // 도서 사진(url)

    @Column
    private Long buyNum; // 누적구매량

    @Column
    private Boolean isNew; // 신간도서 구분

    @Column
    private Boolean hasEbook; // e북 포함여부

    public void patch(Book book) {
        if (book.getBookname() != null)
            this.bookname = book.bookname;
        if (book.getAuthor() != null)
            this.author = book.author;
        if (book.getPublisher() != null)
            this.publisher = book.publisher;
        if (book.getPrice() != null)
            this.price = book.price;
        if (book.getCategory() != null)
            this.category = book.category;
        if (book.getReleasedate() != null)
            this.releasedate = book.releasedate;
        if (book.getImageurl() != null)
            this.imageurl = book.imageurl;
        if (book.getUnitsinstock() != null)
            this.unitsinstock = book.unitsinstock;
        if (book.getDescription() != null)
            this.description = book.description;
        if (book.getBuyNum() != null)
            this.buyNum = book.buyNum;
        if (book.getIsNew() != null)
            this.isNew = book.isNew;
        if(book.getHasEbook() != null)
            this.hasEbook = book.hasEbook;
    }
}
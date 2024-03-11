package com.example.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String bookName;

    @Column
    private String author;

    @Column
    private String publisher;

    @Column
    private Long price;

    @Column
    private String category;

    @Column
    private String releaseDate;

    @Column
    private String imageUrl;

    @Column
    private Long unitsInStock;


    public void patch(Book book) {
        if(book.getBookName() != null)
            this.bookName = book.bookName;
        if(book.getAuthor() != null)
            this.author = book.author;
        if(book.getPublisher() != null)
            this.publisher = book.publisher;
        if(book.getPrice() != null)
            this.price = book.price;
        if(book.getCategory() != null)
            this.category = book.category;
        if(book.getReleaseDate() != null)
            this.releaseDate = book.releaseDate;
        if(book.getImageUrl() != null)
            this.imageUrl = book.imageUrl;
        if(book.getUnitsInStock() != null)
            this.unitsInStock = book.unitsInStock;
    }
}

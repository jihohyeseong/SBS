package com.example.bookstore.repository;

import com.example.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import projection.BookNameAndUnitsInStockProjection;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByOrderByBuyNumDesc();

    List<Book> findByCategory(String category);

    List<Book> findByIsNew(Boolean isNew);

    List<Book> findByBooknameContaining(String bookname);

    List<BookNameAndUnitsInStockProjection> findAllBy();

    List<Book> findByHasEbook(Boolean hasEbook);
}

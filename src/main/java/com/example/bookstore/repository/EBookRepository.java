package com.example.bookstore.repository;

import com.example.bookstore.entity.EBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EBookRepository extends JpaRepository<EBook, Long> {

    EBook findByBookIdAndPage(Long bookId, Long page);
}

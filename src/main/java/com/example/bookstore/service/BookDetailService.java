package com.example.bookstore.service;

import com.example.bookstore.dto.BookDetailDto;
import com.example.bookstore.entity.BookDetail;
import com.example.bookstore.repository.BookDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookDetailService {

    @Autowired
    private BookDetailRepository bookDetailRepository;

    public BookDetailDto getDetail(Long bookId) {
        BookDetail bookDetail = bookDetailRepository.findByBookId(bookId);
        return BookDetailDto.createBookDetailDto(bookDetail);
    }
}

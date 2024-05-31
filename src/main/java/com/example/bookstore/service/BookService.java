package com.example.bookstore.service;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.entity.Book;
import com.example.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import projection.BookNameAndUnitsInStockProjection;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book create(BookDto dto) {
        Book book = dto.toEntity();
        book.setBuyNum(0L);
        if(book.getId() != null)
            return null;
        return bookRepository.save(book);
    }

    public List<Book> index() {
        return bookRepository.findAll();
    }

    public Book show(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book update(Long id, BookDto dto) {
        Book book = dto.toEntity();
        Book target = bookRepository.findById(id).orElse(null);
        if(target == null || id != book.getId())
            return null;
        target.patch(book);
        Book updated = bookRepository.save(target);
        return updated;
    }


    public Book delete(Long id) {
        Book target = bookRepository.findById(id).orElse(null);
        if(target == null)
            return null;
        bookRepository.delete(target);
        return target;
    }

    public List<Book> rankingIndex() {
        return bookRepository.findAllByOrderByBuyNumDesc();
    }

    public List<Book> categoryIndex(String category) {
        return bookRepository.findByCategory(category);
    }

    public List<Book> newIndex() {
        return bookRepository.findByIsNew(Boolean.TRUE);
    }

    public List<Book> searchIndex(String bookname) {
        return bookRepository.findByBooknameContaining(bookname);
    }

    public List<BookNameAndUnitsInStockProjection> adminStockCheck() {
        return bookRepository.findAllBy();
    }

    public List<Book> ebookIndex() {
        return bookRepository.findByHasEbook(Boolean.TRUE);
    }
}

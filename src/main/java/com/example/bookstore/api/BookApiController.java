package com.example.bookstore.api;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.entity.Book;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projection.BookNameAndUnitsInStockProjection;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class BookApiController {
    @Autowired
    private BookService bookService;

    @GetMapping("/api/books")
    public List<Book> index(){
        return bookService.index();
    }

    @GetMapping("/api/admin/books")
    public List<Book> indexAdmin(){
        return bookService.index();
    }

    @GetMapping("/api/books/{id}")
    public Book show(@PathVariable Long id){
        return bookService.show(id);
    }

    @GetMapping("/api/books/ranking")
    public List<Book> rankingIndex(){
        return bookService.rankingIndex();
    }

    @GetMapping("/api/books/ebook")
    public List<Book> ebookIndex(){
        return bookService.ebookIndex();
    }

    @GetMapping("/api/{category}/books")
    public List<Book> categoryIndex(@PathVariable String category){
        return bookService.categoryIndex(category);
    }

    @GetMapping("/api/books/new")
    public List<Book> newIndex(){
        return bookService.newIndex();
    }

    @GetMapping("/api/books/search/{bookname}")
    public List<Book> searchIndex(@PathVariable String bookname){
        return bookService.searchIndex(bookname);
    }

    @PostMapping("/api/books")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> create(@RequestBody BookDto dto){
        Book created = bookService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/books/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody BookDto dto){
        Book updated = bookService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/books/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> delete(@PathVariable Long id){
        Book deleted = bookService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/api/admin/stock") // 관리자페이지 책 재고확인
    public ResponseEntity<List<BookNameAndUnitsInStockProjection>> stockCheck(){
        List<BookNameAndUnitsInStockProjection> books = bookService.adminStockCheck();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }
}

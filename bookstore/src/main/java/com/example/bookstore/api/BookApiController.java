package com.example.bookstore.api;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.entity.Book;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookApiController {
    @Autowired
    private BookService bookService;

    @GetMapping("/api/books")
    public List<Book> index(){
        return bookService.index();
    }

    @GetMapping("/api/books/{id}")
    public Book show(@PathVariable Long id){
        return bookService.show(id);
    }

    @PostMapping("/api/books")
    public ResponseEntity<Book> create(@RequestBody BookDto dto){
        Book created = bookService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/books/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody BookDto dto){
        Book updated = bookService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id){
        Book deleted = bookService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

package com.example.bookstore.api;

import com.example.bookstore.dto.BookDetailDto;
import com.example.bookstore.service.BookDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class BookDetailApiController {

    @Autowired
    private BookDetailService bookDetailService;

    @GetMapping("/api/{bookId}/detail")
    public ResponseEntity<BookDetailDto> bookDetail(@PathVariable Long bookId){
        BookDetailDto dto = bookDetailService.getDetail(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}

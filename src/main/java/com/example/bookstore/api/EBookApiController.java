package com.example.bookstore.api;

import com.example.bookstore.dto.EBookDto;
import com.example.bookstore.service.EBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class EBookApiController {

    @Autowired
    private EBookService eBookService;

    @GetMapping("/api/ebook/{bookId}/{page}")
    public ResponseEntity<EBookDto> getEBook(@PathVariable Long bookId, @PathVariable Long page){
        EBookDto dto = eBookService.getEBook(bookId, page);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}

package com.example.bookstore.service;

import com.example.bookstore.dto.EBookDto;
import com.example.bookstore.entity.EBook;
import com.example.bookstore.repository.EBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EBookService {

    @Autowired
    private EBookRepository eBookRepository;

    public EBookDto getEBook(Long bookId, Long page) {
        EBook eBook = eBookRepository.findByBookIdAndPage(bookId, page);
        return EBookDto.createEBookDto(eBook);
    }
}

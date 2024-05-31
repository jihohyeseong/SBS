package com.example.bookstore.dto;

import com.example.bookstore.entity.BookDetail;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookDetailDto {

    private Long id;
    private Long bookId;
    private String detailUrl;

    public static BookDetailDto createBookDetailDto(BookDetail bookDetail) {
        return new BookDetailDto(
                bookDetail.getId(),
                bookDetail.getBook().getId(),
                bookDetail.getDetailUrl()
                );
    }
}

package com.example.bookstore.dto;

import com.example.bookstore.entity.EBook;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EBookDto {

    private Long id;
    private Long bookId;
    private Long page;
    private String contentKo;
    private String contentEn;

    public static EBookDto createEBookDto(EBook eBook) {
        return new EBookDto(
                eBook.getId(),
                eBook.getBook().getId(),
                eBook.getPage(),
                eBook.getContentKo(),
                eBook.getContentEn()
                );
    }
}

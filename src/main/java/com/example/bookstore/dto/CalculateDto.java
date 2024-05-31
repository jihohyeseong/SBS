package com.example.bookstore.dto;

import com.example.bookstore.entity.Purchase;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CalculateDto {
    private Long id;
    private String username;
    private Long quantity;
    private String bookname;
    private Long price;

    public static CalculateDto createCalculateDto(Purchase purchase) {
        return new CalculateDto(
                purchase.getId(),
                purchase.getUser().getUsername(),
                purchase.getQuantity(),
                purchase.getBook().getBookname(),
                null
        );
    }
}


package com.example.bookstore.dto;

import com.example.bookstore.entity.Purchase;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseDto {
    private Long id;
    private Long userId;
    private Long bookId;
    private Long quantity;
    private String bookname;
    private Long price;
    private String author;
    private String publisher;
    private String imageUrl;

    public static PurchaseDto createOrderDto(Purchase purchase) {
        return new PurchaseDto(
                purchase.getId(),
                purchase.getUser().getId(),
                purchase.getBook().getId(),
                purchase.getQuantity(),null,null,null,null,null);
    }
}

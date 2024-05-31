package com.example.bookstore.dto;

import com.example.bookstore.entity.Cart;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CartDto {
    private Long id;
    private Long userId;
    private Long bookId;
    private Long quantity;
    private String bookname;
    private Long price;
    private String author;
    private String publisher;
    private String imageUrl;

    public static CartDto createCartDto(Cart cart) {
        return new CartDto(
                cart.getId(),
                cart.getUser().getId(),
                cart.getBook().getId(),
                cart.getQuantity(),null,null,null,null,null);
    }
}

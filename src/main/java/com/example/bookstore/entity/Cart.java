package com.example.bookstore.entity;

import com.example.bookstore.dto.CartDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column
    private Long quantity;

    public static Cart createCart(User user, Book book, CartDto dto) {
        if(dto.getId() != null)
            throw new IllegalArgumentException("장바구니 등록 실패. 장바구니 id가 없어야 합니다");
        if(dto.getBookId() != book.getId())
            throw new IllegalArgumentException("장바구니 등록 실패. 장바구니 id가 잘못되었습니다.");
        return new Cart(dto.getId(), user, book, dto.getQuantity());
    }
}

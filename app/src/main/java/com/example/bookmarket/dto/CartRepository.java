package com.example.bookmarket.dto;

import com.example.bookmarket.model.Book;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {
    private static CartRepository instance;
    private List<CartDto> cartBooks = new ArrayList<>(); // 도서 관리 객체 변수

    private CartRepository() {}

    public static CartRepository getInstance() {
        if (instance == null) {
            instance = new CartRepository();
        }
        return instance;
    }

    public void addCartItems(CartDto cartItem) {
        synchronized (cartBooks) {
            boolean isFound = false;
            for (CartDto existingItem : cartBooks) {
                if (existingItem.getBookId().equals(cartItem.getBookId())) {
                    existingItem.setQuantity(existingItem.getQuantity() + 1);
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                cartItem.setQuantity(1L);
                cartItem.setCheck(true);
                cartBooks.add(cartItem);
            }
        }
    }

    public void removeCartItem(CartDto cartItem) {
        synchronized (cartBooks) {
            cartBooks.remove(cartItem);
        }
    }

    public int countCartItems() { // 장바구니에 담긴 도서의 총개수 산출 메소드
        int totalQuantity = 0;
        for (CartDto cartBook : cartBooks) {
            totalQuantity += cartBook.getQuantity();
        }
        return totalQuantity;
    }

    public int grandTotalCartItems() { // 장바구니에 담긴 도서의 합계 산출 메소드
        int totalPrice = 0;
        for (CartDto cartBook : cartBooks) {
            if (cartBook.isCheck()) {
                totalPrice += cartBook.getPrice() * cartBook.getQuantity();

            }
        }
        return totalPrice;
    }

    // 수정된 부분: cartBooks 변수에 대한 접근을 위한 메소드 추가
    public List<CartDto> getCartBooks() {
        return cartBooks;
    }


}
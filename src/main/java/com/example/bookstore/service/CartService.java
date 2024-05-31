package com.example.bookstore.service;

import com.example.bookstore.dto.CartDto;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Cart;
import com.example.bookstore.entity.User;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CartRepository;
import com.example.bookstore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<CartDto> cartinfo(Long userId) {
        List<Cart> cartList = cartRepository.findByUserId(userId);
        List<CartDto> dtos = new ArrayList<>();
        for(int i = 0; i < cartList.size(); i++){
            Cart cart = cartList.get(i);
            CartDto dto = CartDto.createCartDto(cart);
            Book book = cart.getBook();
            dto.setBookname(book.getBookname());
            dto.setPrice(book.getPrice());
            dto.setAuthor(book.getAuthor());
            dto.setPublisher(book.getPublisher());
            dto.setImageUrl(book.getImageurl());
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public CartDto addCart(Long userId, Long bookId, CartDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 등록 실패. 로그인 필요"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 등록 실패. 대상 책 없음"));
        List<Cart> cartList = cartRepository.findByUserId(userId);
        for(int i = 0; i< cartList.size(); i++){
            Cart cart = cartList.get(i);
            if(cart.getBook().getId() == bookId){
                dto.setQuantity(dto.getQuantity() + cart.getQuantity());
                cartRepository.delete(cart);
            }
        }
        Cart newCart = Cart.createCart(user, book, dto);
        Cart created = cartRepository.save(newCart);
        return CartDto.createCartDto(created);
    }

    @Transactional
    public CartDto deleteCart(Long userId, Long cartId) {
        List<Cart> cartList = cartRepository.findByUserId(userId);
        for(int i = 0; i < cartList.size(); i++){
            Cart cart = cartList.get(i);
            if(cart.getId() == cartId){
                cartRepository.delete(cart);
                return CartDto.createCartDto(cart);
            }
        }
        throw new IllegalArgumentException("해당 책이 카트에 존재하지 않습니다.");
    }

    @Transactional
    public List<Cart> deleteCartAll(Long userId) {
        List<Cart> cartList = cartRepository.findByUserId(userId);
        cartRepository.deleteAll(cartList);
        return cartList;
    }
}

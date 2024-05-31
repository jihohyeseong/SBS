package com.example.bookstore.api;

import com.example.bookstore.dto.CartDto;
import com.example.bookstore.dto.CustomUserDetails;
import com.example.bookstore.entity.Cart;
import com.example.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CartApiController {

    @Autowired
    private CartService cartService;

    @GetMapping("/api/cart")
    public ResponseEntity<List<CartDto>> cartList(@AuthenticationPrincipal CustomUserDetails userDetails){
        Long userId = userDetails.getUserId();
        List<CartDto> cartDtos = cartService.cartinfo(userId);
        return ResponseEntity.status(HttpStatus.OK).body(cartDtos);
    }

    @PostMapping("/api/books/{bookId}/cart")
    public ResponseEntity<CartDto> addCart(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestBody CartDto dto, @PathVariable Long bookId){
        Long userId = userDetails.getUserId();
        CartDto item = cartService.addCart(userId, bookId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @DeleteMapping("/api/books/{cartId}/cart") // 선택 삭제
    public ResponseEntity<CartDto> deleteCart(@AuthenticationPrincipal CustomUserDetails userDetails,
                                              @PathVariable Long cartId){
        Long userId = userDetails.getUserId();
        CartDto deleted = cartService.deleteCart(userId, cartId);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }

    @DeleteMapping("/api/books/all/cart") // 전체 삭제
    public ResponseEntity<List<Cart>> deleteAll(@AuthenticationPrincipal CustomUserDetails userDetails){
        Long userId = userDetails.getUserId();
        List<Cart> deleted = cartService.deleteCartAll(userId);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }
}

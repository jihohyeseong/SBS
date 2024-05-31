package com.example.bookstore.service;

import com.example.bookstore.dto.JoinDto;
import com.example.bookstore.entity.Cart;
import com.example.bookstore.entity.Comment;
import com.example.bookstore.entity.Purchase;
import com.example.bookstore.entity.User;
import com.example.bookstore.repository.CartRepository;
import com.example.bookstore.repository.CommentRepository;
import com.example.bookstore.repository.PurchaseRepository;
import com.example.bookstore.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserinfoService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User userinfo(String username) {
        log.info(username);
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User update(String username, JoinDto dto) {
        User target = userRepository.findByUsername(username);

        target.setUsername(username);
        target.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        target.setEmail(dto.getEmail());
        target.setPhoneNum(dto.getPhoneNum());
        target.setAddress(dto.getAddress());
        target.setRole("ROLE_USER");

        User updated = userRepository.save(target);

        return updated;
    }

    @Transactional
    public User delete(String username, Long userId) {
        List<Comment> comments = commentRepository.findByUsername(username);
        commentRepository.deleteAll(comments);
        List<Cart> cartList = cartRepository.findByUserId(userId);
        cartRepository.deleteAll(cartList);
        List<Purchase> purchaseList = purchaseRepository.findByUserId(userId);
        purchaseRepository.deleteAll(purchaseList);
        User user = userRepository.findByUsername(username);
        if(user == null)
            return null;
        userRepository.delete(user);

        return user;
    }
}

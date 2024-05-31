package com.example.bookstore.service;

import com.example.bookstore.dto.JoinDto;
import com.example.bookstore.entity.User;
import com.example.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public User joinProcess(JoinDto dto){

        // db에 이미 동일한 username을 가진 회원이 존재하는지
        boolean isUser = userRepository.existsByUsername(dto.getUsername());
        if(isUser)
            return null;

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setPhoneNum(dto.getPhoneNum());
        user.setAddress(dto.getAddress());
        user.setRole("ROLE_USER");

        return userRepository.save(user);
    }

    public boolean isUsernameUnique(String username) {
        return userRepository.existsByUsername(username);
    }
}

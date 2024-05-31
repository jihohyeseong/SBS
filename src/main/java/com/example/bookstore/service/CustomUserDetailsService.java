package com.example.bookstore.service;

import com.example.bookstore.dto.CustomUserDetails;
import com.example.bookstore.entity.User;
import com.example.bookstore.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userData = userRepository.findByUsername(username);

        if(userData != null) {
            log.info("로그인 시도 아이디: " + username);
            return new CustomUserDetails(userData);
        }

        else {
            log.info("로그인 실패 아이디: " + username);
            throw new UsernameNotFoundException("user not found:" + username);
        }
    }
}

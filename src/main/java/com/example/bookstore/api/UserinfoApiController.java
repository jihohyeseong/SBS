package com.example.bookstore.api;

import com.example.bookstore.dto.CustomUserDetails;
import com.example.bookstore.dto.JoinDto;
import com.example.bookstore.entity.User;
import com.example.bookstore.service.UserinfoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class UserinfoApiController {

    @Autowired
    UserinfoService userinfoService;

    @GetMapping("/api/mypage") // 현재 로그인된 회원 정보 가져오기
    public ResponseEntity<User> showinfo(@AuthenticationPrincipal CustomUserDetails userDetails){
        log.info("api불러오기:"+userDetails.getUsername());
        String username = userDetails.getUsername();
        User userinfo = userinfoService.userinfo(username);
        return ResponseEntity.status(HttpStatus.OK).body(userinfo);
    }

    @PatchMapping("/api/mypage") // 회원 정보 수정
    public ResponseEntity<?> updateinfo(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @Valid @RequestBody JoinDto dto,
                                           BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsMap);
        }
            String username = userDetails.getUsername();
        User updatedInfo = userinfoService.update(username, dto);
        return (updatedInfo != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updatedInfo):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/mypage") // 회원 탈퇴, 탈퇴 전 장바구니, 댓글등 먼저 삭제
    public ResponseEntity<User> deleteinfo(@AuthenticationPrincipal CustomUserDetails userDetails){
        String username = userDetails.getUsername();
        Long userId = userDetails.getUserId();
        User deletedInfo = userinfoService.delete(username, userId);
        return (deletedInfo != null) ?
                ResponseEntity.status(HttpStatus.OK).build():
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

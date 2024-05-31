package com.example.bookstore.api;

import com.example.bookstore.dto.JoinDto;
import com.example.bookstore.entity.User;
import com.example.bookstore.service.JoinService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class JoinApiController {

    @Autowired
    private JoinService joinService;

    @PostMapping("/api/joinProc")
    public ResponseEntity<?> join(@Valid @RequestBody JoinDto dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsMap);
        }

        if (joinService.isUsernameUnique(dto.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User created = joinService.joinProcess(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

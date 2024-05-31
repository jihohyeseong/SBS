package com.example.bookstore.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
public class ImageUploadApiController {

    @Value("${upload.dir}")
    private String uploadDir;

    @PostMapping("/api/upload")
    public String uploadImage(@RequestParam("image") MultipartFile image){  // 리액트에서 image 받아오기 name = "image"로 할 것
        // 이미지 파일을 업로드할 디렉토리 생성
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        // 이미지 파일의 저장 경로와 파일 이름 생성
        String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        String filePath = uploadDir + "/" + fileName;

        // 이미지 파일을 서버에 저장
        File dest = new File(filePath);
        try {
            image.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            // 또는 오류를 로그에 기록하거나 사용자에게 오류 메시지를 표시
        }

        // 이미지 파일의 URL 반환
        return "http://localhost:8080/images/" + fileName;
    }
}

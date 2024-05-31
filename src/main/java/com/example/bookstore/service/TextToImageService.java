package com.example.bookstore.service;

import com.example.bookstore.dto.EBookDto;
import com.example.bookstore.dto.TextToImageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class TextToImageService {

    static final String REST_API_KEY = "${}";
    private TextToImageResponse textToImage;

    public TextToImageResponse textToImage(EBookDto eBookDto){
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("version", "v2.1");
        parameters.put("prompt", eBookDto.getContentEn());
        parameters.put("width", 1024);
        parameters.put("height", 512);
        parameters.put("image_format", "png");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        RestTemplate restTemplate = new RestTemplate();

        textToImage = restTemplate.postForObject(
                "https://api.kakaobrain.com/v2/inference/karlo/t2i",
                requestEntity,
                TextToImageResponse.class
        );

        return textToImage;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = " KakaoAK " + REST_API_KEY;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/json");

        return httpHeaders;
    }
}

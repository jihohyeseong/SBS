package com.example.bookstore.service;

import com.example.bookstore.dto.EBookDto;
import com.example.bookstore.dto.TextToSpeechResponse;
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
public class TextToSpeechService {

    static final String API_TOKEN = "${}";
    private TextToSpeechResponse textToSpeech;

    public TextToSpeechResponse textToSpeech(EBookDto eBookDto){
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("actor_id", "65a8c7e1915a6295614f775b");
        parameters.put("text", eBookDto.getContentKo());
        parameters.put("lang", "auto");
        parameters.put("xapi_hd", true);
        parameters.put("model_version", "latest");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        RestTemplate restTemplate = new RestTemplate();

        textToSpeech = restTemplate.postForObject(
                "https://typecast.ai/api/speak",
                requestEntity,
                TextToSpeechResponse.class
        );

        return textToSpeech;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = " Bearer " + API_TOKEN;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/json");

        return httpHeaders;
    }

}

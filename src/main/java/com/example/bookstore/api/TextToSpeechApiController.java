package com.example.bookstore.api;

import com.example.bookstore.dto.EBookDto;
import com.example.bookstore.dto.TextToSpeechResponse;
import com.example.bookstore.service.TextToSpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class TextToSpeechApiController {

    @Autowired
    private TextToSpeechService textToSpeechService;

    @PostMapping("/api/texttospeech")
    public TextToSpeechResponse textToSpeech(@RequestBody EBookDto eBookDto){
        return textToSpeechService.textToSpeech(eBookDto);
    }
}

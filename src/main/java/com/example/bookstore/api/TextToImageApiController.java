package com.example.bookstore.api;

import com.example.bookstore.dto.EBookDto;
import com.example.bookstore.dto.TextToImageResponse;
import com.example.bookstore.service.TextToImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class TextToImageApiController {

    @Autowired
    private TextToImageService textToImageService;

    @PostMapping("/api/texttoimage")
    public TextToImageResponse textToImage(@RequestBody EBookDto eBookDto){
        return textToImageService.textToImage(eBookDto);
    }
}

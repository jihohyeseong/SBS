package com.example.bookstore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.*;

@Getter
@Setter
@ToString
public class TextToImageResponse {

    private String id;
    private String model_version;
    private Image[] images;
}

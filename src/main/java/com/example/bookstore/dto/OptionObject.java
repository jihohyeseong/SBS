package com.example.bookstore.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OptionObject {
    private String language = "ko";
    private Integer tone = 2;
}

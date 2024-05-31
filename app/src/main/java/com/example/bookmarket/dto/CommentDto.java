package com.example.bookmarket.dto;

public class CommentDto {
    private Long bookId;
    private String username;
    private String content;
    private String body;

    // 생성자
    public CommentDto(Long bookId, String username, String body, String content) {
        this.bookId = bookId;
        this.username = username;
        this.body = body;
        this.content = content;
    }

    // 게터 메서드
    public Long getBookId() {
        return bookId;
    }

    public String getUsername() {
        return username;
    }

    public String getBody() {
        return body;
    }

    public String getContent(){return content;}

    public int getComment() {
        return 0;
    }
}
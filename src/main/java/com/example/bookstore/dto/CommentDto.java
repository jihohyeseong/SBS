package com.example.bookstore.dto;

import com.example.bookstore.entity.Comment;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentDto {

    private Long id;
    private Long bookId;
    private String username;
    private String content;
    private String bookname;
    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getBook().getId(),
                comment.getUsername(),
                comment.getContent(),
                null
        );
    }
}

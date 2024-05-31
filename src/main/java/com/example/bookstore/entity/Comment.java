package com.example.bookstore.entity;

import com.example.bookstore.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column
    private String username;

    @Column
    private String content;

    public static Comment createComment(String username, CommentDto dto, Book book) {
        if(dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패. 댓글의 id가 없어야 합니다.");
        if(dto.getBookId() != book.getId())
            throw new IllegalArgumentException("댓글 생성 실패. 책 id가 잘못되었습니다.");
        return new Comment(dto.getId(), book, username, dto.getContent());
    }

    public void patch(CommentDto dto) {
        if(this.id != dto.getId())
            throw new IllegalArgumentException("댓글 수정 실패. 잘못된 id가 입력되었습니다");
        if(dto.getContent() != null)
            this.content = dto.getContent();
    }
}

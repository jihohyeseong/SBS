package com.example.bookstore.service;

import com.example.bookstore.dto.CommentDto;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Comment;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<CommentDto> comments(Long bookId) {
        List<Comment> comments = commentRepository.findByBookId(bookId);
        List<CommentDto> dtos = new ArrayList<>();
        for(int i = 0; i < comments.size(); i++){
            Comment c = comments.get(i);
            CommentDto dto = CommentDto.createCommentDto(c);
            dto.setBookname(c.getBook().getBookname());
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public CommentDto create(String username, Long bookId, CommentDto dto) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패. 대상 책 없음"));
        Comment comment = Comment.createComment(username, dto, book);
        Comment created = commentRepository.save(comment);
        CommentDto createdDto = CommentDto.createCommentDto(created);
        createdDto.setBookname(created.getBook().getBookname());
        return createdDto;
    }

    @Transactional
    public CommentDto update(String username, Long id, CommentDto dto) {
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패. 대상 댓글이 없음"));
        if (!target.getUsername().equals(username)) {
            throw new IllegalArgumentException("댓글 수정 실패. 대상 댓글의 작성자가 아니고 권한이 없음");
        }
        target.patch(dto);
        Comment updated = commentRepository.save(target);
        CommentDto updatedDto = CommentDto.createCommentDto(updated);
        updatedDto.setBookname(updated.getBook().getBookname());
        return updatedDto;
    }

    @Transactional
    public CommentDto delete(String username, Long id) {
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패. 대상이 없습니다"));
        if (!target.getUsername().equals(username)) {
            throw new IllegalArgumentException("댓글 삭제 실패. 대상 댓글의 작성자가 아니고 권한이 없음");
        }
        commentRepository.delete(target);
        return CommentDto.createCommentDto(target);
    }

    public List<CommentDto> myComments(String username) {
        List<Comment> comments = commentRepository.findByUsername(username);
        List<CommentDto> dtos = new ArrayList<>();
        for(int i = 0; i < comments.size(); i++){
            Comment c = comments.get(i);
            CommentDto dto = CommentDto.createCommentDto(c);
            dto.setBookname(c.getBook().getBookname());
            dtos.add(dto);
        }
        return dtos;
    }

    public CommentDto deleteAsAdmin(Long id) {
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패. 대상이 없습니다"));
        commentRepository.delete(target);
        return CommentDto.createCommentDto(target);
    }
}

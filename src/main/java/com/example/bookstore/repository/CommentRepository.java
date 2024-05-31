package com.example.bookstore.repository;

import com.example.bookstore.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 책의 모든 댓글 조회
    @Query(value =
            "SELECT * "+
            "FROM comment "+
            "WHERE book_id = :bookId",
            nativeQuery = true)
    List<Comment> findByBookId(Long bookId);
    // 특정 유저의 모든 댓글 조회
    @Query(value =
            "SELECT * "+
            "FROM comment "+
            "WHERE username = :username",
            nativeQuery = true)
    List<Comment> findByUsername(String username);
}

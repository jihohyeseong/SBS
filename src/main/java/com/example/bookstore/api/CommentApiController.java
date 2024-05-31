package com.example.bookstore.api;

import com.example.bookstore.dto.CommentDto;
import com.example.bookstore.dto.CustomUserDetails;
import com.example.bookstore.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/api/books/{bookId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long bookId){
        List<CommentDto> dtos = commentService.comments(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping("/api/mypage/comments")
    public ResponseEntity<List<CommentDto>> myComments(@AuthenticationPrincipal CustomUserDetails userDetails){
        String username = userDetails.getUsername();
        List<CommentDto> dtos = commentService.myComments(username);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PostMapping("/api/books/{bookId}/comments")
    public ResponseEntity<CommentDto> create(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @PathVariable Long bookId, @RequestBody CommentDto dto){
        String username = userDetails.getUsername();
        CommentDto createdDto = commentService.create(username, bookId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @PathVariable Long id, @RequestBody CommentDto dto){
        String username = userDetails.getUsername();
        CommentDto updated = commentService.update(username, id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @PathVariable Long id){
        String username = userDetails.getUsername();
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        CommentDto deleted;
        if (isAdmin) {
            // 관리자는 모든 댓글을 삭제할 수 있음
            deleted = commentService.deleteAsAdmin(id);
        } else {
            // 일반 사용자는 자신의 댓글만 삭제할 수 있음
            deleted = commentService.delete(username, id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }
}

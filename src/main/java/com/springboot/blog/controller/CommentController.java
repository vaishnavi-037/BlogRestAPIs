package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentRequestDto;
import com.springboot.blog.payload.CommentResponseDto;
import com.springboot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable(value = "postId") Long postId,
                                                            @Valid @RequestBody CommentRequestDto comment) {
        CommentResponseDto newComment = commentService.createComment(postId, comment);
        return new ResponseEntity<>(newComment, CREATED);
    }

    @GetMapping("posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(@PathVariable(value = "postId") Long postId) {
        List<CommentResponseDto> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments, OK);
    }

    @GetMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable(value = "postId") Long postId, @PathVariable(value = "id") Long id) {
        CommentResponseDto comment = commentService.getCommentById(postId, id);
        return new ResponseEntity<>(comment, OK);
    }

    @PutMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable(value = "postId") Long postId,
                                                            @PathVariable(value = "id") Long id,
                                                            @Valid @RequestBody CommentRequestDto comment) {
        CommentResponseDto updatedComment = commentService.updateComment(postId, id, comment);
        return new ResponseEntity<>(updatedComment, OK);
    }

    @DeleteMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(value = "postId") Long postId,
                                             @PathVariable(value = "id") Long id) {
        commentService.deleteComment(postId, id);
        return new ResponseEntity<>("Comment deleted successfully", OK);
    }
}

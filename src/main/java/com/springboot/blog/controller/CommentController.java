package com.springboot.blog.controller;

import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentRequestDto;
import com.springboot.blog.payload.CommentResponseDto;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

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
                                                            @RequestBody CommentRequestDto comment) {
        try {
            CommentResponseDto newComment = commentService.createComment(postId, comment);
            return new ResponseEntity<>(newComment, CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(@PathVariable(value = "postId") Long postId) {
        try {
            List<CommentResponseDto> comments = commentService.getCommentsByPostId(postId);
            return new ResponseEntity<>(comments, OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable(value = "postId") Long postId, @PathVariable(value = "id") Long id) {
        try {
            CommentResponseDto comment = commentService.getCommentById(postId, id);
            return new ResponseEntity<>(comment, OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        } catch (BlogApiException e) {
            return new ResponseEntity<>(e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable(value = "postId") Long postId,
                                                            @PathVariable(value = "id") Long id,
                                                            @RequestBody CommentRequestDto comment) {
        try {
            CommentResponseDto updatedComment = commentService.updateComment(postId, id, comment);
            return new ResponseEntity<>(updatedComment, OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        } catch (BlogApiException e) {
            return new ResponseEntity<>(e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(value = "postId") Long postId,
                                             @PathVariable(value = "id") Long id) {
        try {
            commentService.deleteComment(postId, id);
            return new ResponseEntity<>("Comment deleted successfully", OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        } catch (BlogApiException e) {
            return new ResponseEntity<>(e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }
}

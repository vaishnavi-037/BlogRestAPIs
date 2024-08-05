package com.springboot.blog.service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.*;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // we can omit @Autowired because it has only 1 param. From spring 4.3 onwards, if a class is configured as a spring bean and it has only one constructor,
    // then we can omit the @Autowired annotation
    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public CommentResponseDto createComment(Long postId, CommentRequestDto requestComment) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        Comment comment = requestComment.toCommentEntity(post);

        Comment saveComment = commentRepository.save(comment);
        return saveComment.toCommentDto();
    }

    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> comment.toCommentDto()).collect(Collectors.toList());
    }

    public CommentResponseDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));

        if(!comment.getPost().getId().equals(post.getId())) throw new BlogApiException(BAD_REQUEST, "Comment does not belong to post");

        return comment.toCommentDto();
    }

    public CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto requestComment) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        Comment existingComment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));

        if(!existingComment.getPost().getId().equals(post.getId())) throw new BlogApiException(BAD_REQUEST, "Comment does not belong to post");

        existingComment.setName(requestComment.getName());
        existingComment.setEmail(requestComment.getEmail());
        existingComment.setBody(requestComment.getBody());

        Comment updatedComment = commentRepository.save(existingComment);
        return updatedComment.toCommentDto();
    }

    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));

        if(!comment.getPost().getId().equals(post.getId())) throw new BlogApiException(BAD_REQUEST, "Comment does not belong to post");

        commentRepository.delete(comment);
    }
}

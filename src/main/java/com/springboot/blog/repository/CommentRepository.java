package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // The query retrieves all rows from the comments table where the post_id column matches the provided postId.
    // Each row corresponds to a Comment entity.
    List<Comment> findByPostId(long postId);
}

package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByTitle(String title);
    List<Post> findByCategoryId(Long categoryId);

    //JPQL query
    @Query("SELECT p FROM posts p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%',:query,'%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%',:query,'%'))")
    List<Post> searchPosts(@Param("query") String query);
}

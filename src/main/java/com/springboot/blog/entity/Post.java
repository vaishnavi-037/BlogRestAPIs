package com.springboot.blog.entity;

import com.springboot.blog.payload.CommentResponseDto;
import com.springboot.blog.payload.PostResponseDto;
import com.springboot.blog.payload.PostWithCommentsDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "posts")
@Table(name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true) //one Post belong to many Comments
    private Set<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public PostResponseDto toPostDto() {
        return new PostResponseDto(id, title, description, content);
    }

    public PostWithCommentsDto toPostCommentDto(Set<CommentResponseDto> comments) {
        return new PostWithCommentsDto(id, title, description, content, comments);
    }
}

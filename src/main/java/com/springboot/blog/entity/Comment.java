package com.springboot.blog.entity;

import com.springboot.blog.payload.CommentResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "comments")
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String body;

    // In your Comment entity, the post field is annotated with @ManyToOne and @JoinColumn(name = "post_id").
    // This means each Comment is linked to a Post through the post_id column in the comments table.
    @ManyToOne(fetch = FetchType.LAZY) //many comments belong to one post
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public CommentResponseDto toCommentDto() {
        return new CommentResponseDto(id, name, email, body);
    }
}

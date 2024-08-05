package com.springboot.blog.payload;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentRequestDto {
    private String name;
    private String email;
    private String body;

    public Comment toCommentEntity(Post post) {
        return new Comment(null, name, email, body, post);
    }
}

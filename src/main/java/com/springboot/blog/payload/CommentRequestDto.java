package com.springboot.blog.payload;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentRequestDto {

    @NotEmpty(message = "Name should not be null or empty")
    @NotBlank
    @Size(min = 2, message = "Post tile should have at least 2 characters")
    private String name;

    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    @NotEmpty
    @NotBlank
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    private String body;

    public Comment toCommentEntity(Post post) {
        return new Comment(null, name, email, body, post);
    }
}

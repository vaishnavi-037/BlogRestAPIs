package com.springboot.blog.payload;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;

@Data
@AllArgsConstructor
public class PostRequestDto {

    @NotEmpty
    @NotBlank
    @Size(min = 2, message = "Post tile should have at least 2 characters")
    private String title;

    @NotEmpty
    @NotBlank
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @NotEmpty
    @NotBlank
    private String content;

    private Long categoryId;

    public Post toPostEntity(Category category) {
        return new Post(null, title, description, content, new HashSet<>(),category);
    }
}

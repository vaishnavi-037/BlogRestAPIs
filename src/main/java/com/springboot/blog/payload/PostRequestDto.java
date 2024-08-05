package com.springboot.blog.payload;

import com.springboot.blog.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;

@Data
@AllArgsConstructor
public class PostRequestDto {
    private String title;
    private String description;
    private String content;

    public Post toPostEntity() {
        return new Post(null, title, description, content, new HashSet<>());
    }
}

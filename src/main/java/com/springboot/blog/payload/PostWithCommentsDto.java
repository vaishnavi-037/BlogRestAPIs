package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class PostWithCommentsDto {
    private long id;
    private String title;
    private String description;
    private String content;
    private Set<CommentResponseDto> comments;
}

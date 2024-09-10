package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostResponseDto {
    private long id;
    private String title;
    private String description;
    private String content;
    private Long categoryId;
}

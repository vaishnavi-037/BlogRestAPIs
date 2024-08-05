package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponseDto {
    private long id;
    private String name;
    private String email;
    private String body;
}

package com.springboot.blog.payload;

import com.springboot.blog.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDto {
    private String name;
    private String description;

    public Category toCategoryEntity() {
        return new Category(null, name, description, Collections.emptyList());
    }
}


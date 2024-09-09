package com.springboot.blog.controller;

import com.springboot.blog.payload.CategoryRequestDto;
import com.springboot.blog.payload.CategoryResponseDto;
import com.springboot.blog.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryResponseDto> addCategory(@Valid @RequestBody CategoryRequestDto category) {
        CategoryResponseDto newCategory = categoryService.addCategory(category);
        return new ResponseEntity<>(newCategory, CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable("id") Long categoryId) {
        CategoryResponseDto category = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category, OK);
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@Valid @RequestBody CategoryRequestDto category,
                                                              @PathVariable("id") Long categoryId) {
        CategoryResponseDto updatedCategory = categoryService.updateCategory(category, categoryId);
        return new ResponseEntity<>(updatedCategory, OK);
    }
}

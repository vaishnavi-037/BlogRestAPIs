package com.springboot.blog.service;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CategoryRequestDto;
import com.springboot.blog.payload.CategoryResponseDto;
import com.springboot.blog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDto addCategory(CategoryRequestDto requestCategory) {
        Category category = requestCategory.toCategoryEntity();
        Category savedCategory = categoryRepository.save(category);

        return savedCategory.toCategoryDto();
    }

    public CategoryResponseDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId.toString()));
        return category.toCategoryDto();
    }

    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> category.toCategoryDto()).collect(Collectors.toList());
    }

    public CategoryResponseDto updateCategory(CategoryRequestDto requestCategory, Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId.toString()));

        existingCategory.setName(requestCategory.getName());
        existingCategory.setDescription(requestCategory.getDescription());

        Category saveCategory = categoryRepository.save(existingCategory);
        return saveCategory.toCategoryDto();
    }

    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId.toString()));
        categoryRepository.delete(category);
    }
}

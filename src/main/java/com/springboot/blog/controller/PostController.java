package com.springboot.blog.controller;

import com.springboot.blog.payload.PostPaginationResponse;
import com.springboot.blog.payload.PostRequestDto;
import com.springboot.blog.payload.PostResponseDto;
import com.springboot.blog.payload.PostWithCommentsDto;
import com.springboot.blog.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.blog.utils.AppConstants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "CRUD REST APIs for Post Resource")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Get All Posts with requested page size and page number REST API",
            description = "Get All Posts with requested page size and page number REST API is used to get all Posts in system"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Posts are fetched successfully"
    )
    @GetMapping
    public ResponseEntity<PostPaginationResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {

        PostPaginationResponse posts = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(posts, OK);

    }

    @Operation(
            summary = "Get Post By Id REST API",
            description = "Get Post By Id REST API is used to get a single Post in system"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Posts are fetched successfully"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostWithCommentsDto> getPostById(@PathVariable(name = "id") long id) {
        PostWithCommentsDto post = postService.getPostById(id);
        return new ResponseEntity<>(post, OK);

    }

    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save Post in system"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Post Created successfully"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto post) {
        PostResponseDto newPost = postService.createPost(post);
        return new ResponseEntity<>(newPost, CREATED);

    }

    @Operation(
            summary = "Update Post By Id REST API",
            description = "Update Post By Id REST API is used to update a single Post in system"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Post Updated successfully"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@Valid @RequestBody PostRequestDto post, @PathVariable(name = "id") long id) {
        PostResponseDto updatedPost = postService.updatePost(post, id);
        return new ResponseEntity<>(updatedPost, OK);

    }

    @Operation(
            summary = "Delete Post By Id REST API",
            description = "Delete Post By Id REST API is used to delete a single Post in system"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Post Deleted successfully"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post entity deleted successfully", OK);
    }

    @Operation(
            summary = "Get Posts By Category REST API",
            description = "Get Posts By Category REST API is used to get all Posts in system"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Post Deleted successfully"
    )
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostResponseDto>> getPostByCategory(@PathVariable("id") Long categoryId) {
        List<PostResponseDto> posts = postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(posts, OK);
    }
}

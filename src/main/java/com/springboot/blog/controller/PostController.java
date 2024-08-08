package com.springboot.blog.controller;

import com.springboot.blog.payload.PostPaginationResponse;
import com.springboot.blog.payload.PostRequestDto;
import com.springboot.blog.payload.PostResponseDto;
import com.springboot.blog.payload.PostWithCommentsDto;
import com.springboot.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.springboot.blog.utils.AppConstants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<PostWithCommentsDto> getPostById(@PathVariable(name = "id") long id) {
        PostWithCommentsDto post = postService.getPostById(id);
        return new ResponseEntity<>(post, OK);

    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto post) {
        PostResponseDto newPost = postService.createPost(post);
        return new ResponseEntity<>(newPost, CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@RequestBody PostRequestDto post, @PathVariable(name = "id") long id) {
        PostResponseDto updatedPost = postService.updatePost(post, id);
        return new ResponseEntity<>(updatedPost, OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post entity deleted successfully", OK);

    }
}

package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostPaginationResponse;
import com.springboot.blog.payload.PostRequestDto;
import com.springboot.blog.payload.PostResponseDto;
import com.springboot.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    // we can omit @Autowired because it has only 1 param. From spring 4.3 onwards, if a class is configured as a spring bean and it has only one constructor,
    // then we can omit the @Autowired annotation
    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponseDto createPost(PostRequestDto requestPost) {
        Post post = requestPost.toPostEntity();
        Post savePost = postRepository.save(post);
        return savePost.toPostDto();
    }

    public PostPaginationResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postList = posts.getContent();

        List<PostResponseDto> content = postList.stream().map(Post::toPostDto).toList();

        PostPaginationResponse paginationResponse = new PostPaginationResponse();
        paginationResponse.setContent(content);
        paginationResponse.setPageNo(posts.getNumber());
        paginationResponse.setPageSize(posts.getSize());
        paginationResponse.setTotalElements(posts.getTotalElements());
        paginationResponse.setTotalPages(posts.getTotalPages());
        paginationResponse.setLast(posts.isLast());

        return paginationResponse;
    }

    public PostResponseDto getPostById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id))).toPostDto();
    }

    public PostResponseDto updatePost(PostRequestDto requestPost, long id) {
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));

        existingPost.setTitle(requestPost.getTitle());
        existingPost.setDescription(requestPost.getDescription());
        existingPost.setContent(requestPost.getContent());

        Post savePost = postRepository.save(existingPost);
        return savePost.toPostDto();
    }

    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));
        postRepository.delete(post);
    }
}

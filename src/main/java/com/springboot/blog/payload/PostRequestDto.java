package com.springboot.blog.payload;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;

@Schema(
        description = "Post Request Model Information"
)
@Data
@AllArgsConstructor
public class PostRequestDto {

    @Schema(
            description = "Blog Post Title"
    )
    @NotEmpty
    @NotBlank
    @Size(min = 2, message = "Post tile should have at least 2 characters")
    private String title;

    @Schema(
            description = "Blog Post Description"
    )
    @NotEmpty
    @NotBlank
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @Schema(
            description = "Blog Post Content"
    )
    @NotEmpty
    @NotBlank
    private String content;

    @Schema(
            description = "Blog Post Category Id"
    )
    private Long categoryId;

    public Post toPostEntity(Category category) {
        return new Post(null, title, description, content, new HashSet<>(),category);
    }
}

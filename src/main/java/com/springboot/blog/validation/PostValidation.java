package com.springboot.blog.validation;

import com.springboot.blog.payload.PostRequestDto;
import com.springboot.blog.payload.ValidationErrors;

import java.util.ArrayList;
import java.util.List;

import static com.springboot.blog.payload.ValidationErrors.DESCRIPTION_IS_EMPTY;
import static com.springboot.blog.payload.ValidationErrors.TITLE_IS_EMPTY;

public class PostValidation {

    public List<ValidationErrors> validatePost(PostRequestDto requestPost) {
        List<ValidationErrors> errors = new ArrayList<>();
        if(requestPost.getTitle().isBlank()) errors.add(TITLE_IS_EMPTY);
        if(requestPost.getDescription().isBlank()) errors.add(DESCRIPTION_IS_EMPTY);

        return errors;
    }
}

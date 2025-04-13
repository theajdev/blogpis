package com.aj.blog.blogappapis.payloads;

import java.util.Date;

import com.aj.blog.blogappapis.entities.Category;
import com.aj.blog.blogappapis.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryDto category;
    private UserDto user;
}

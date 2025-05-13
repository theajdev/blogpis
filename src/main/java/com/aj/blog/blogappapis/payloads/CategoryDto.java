package com.aj.blog.blogappapis.payloads;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
    private Integer categoryId;
    
    @NotEmpty
    @Size(min = 4, message = "Category title should be minimum 4 characters !")
    private String categoryTitle;
    @NotEmpty
    @Size(min = 4, message = "Category description should be minimum 4 characters !")
    private String categoryDesc;
}

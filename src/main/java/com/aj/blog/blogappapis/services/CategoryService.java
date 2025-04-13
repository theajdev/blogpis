package com.aj.blog.blogappapis.services;

import java.util.List;

import com.aj.blog.blogappapis.payloads.CategoryDto;

public interface CategoryService {
    // create
    public CategoryDto createCategory(CategoryDto categoryDto);

    // update
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    // delete
    public void deleteCategory(Integer categoryId);

    // get
    public CategoryDto getCategory(Integer categoryId);

    // getAll
    List<CategoryDto> getCategories();
}

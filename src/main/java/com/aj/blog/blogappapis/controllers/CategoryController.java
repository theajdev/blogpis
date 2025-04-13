package com.aj.blog.blogappapis.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aj.AJLogger;
import com.aj.blog.blogappapis.payloads.ApiResponse;
import com.aj.blog.blogappapis.payloads.CategoryDto;
import com.aj.blog.blogappapis.services.CategoryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService catService;

    // Create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto newCategory = this.catService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(newCategory, HttpStatus.CREATED);

    }

    // Update
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
            @PathVariable Integer catId) {
        CategoryDto updatedCategory = this.catService.updateCategory(categoryDto, catId);
        return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Integer catId) {
        this.catService.deleteCategory(catId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully.", true, catId),
                HttpStatus.OK);
    }

    // Get
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {
        CategoryDto category = this.catService.getCategory(catId);

        return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
    }

    // Get All
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = this.catService.getCategories();
        AJLogger.log("BlogApp", "STATUS: " + HttpStatus.OK);
        return ResponseEntity.ok(categories);
    }

}

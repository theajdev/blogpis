package com.aj.blog.blogappapis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aj.blog.blogappapis.entities.Category;
import com.aj.blog.blogappapis.exceptions.ResourceAlreadyExistsException;
import com.aj.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.aj.blog.blogappapis.payloads.CategoryDto;
import com.aj.blog.blogappapis.repositories.CategoryRepo;
import com.aj.blog.blogappapis.services.CategoryService;

@Service
public class CategoryServiceIpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		// Check if title already exists
		if (categoryRepo.existsByCategoryTitle(categoryDto.getCategoryTitle())) {
			throw new ResourceAlreadyExistsException("Category with title already exists!");
		}

		Category newCategory = this.modelMapper.map(categoryDto, Category.class);
		Category addedCategory = this.categoryRepo.save(newCategory);
		return this.modelMapper.map(addedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		// Check if title already exists
		if (categoryRepo.existsByCategoryTitle(categoryDto.getCategoryTitle())) {
			throw new ResourceAlreadyExistsException("Category with title already exists!");
		}

		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDesc(categoryDto.getCategoryDesc());
		Category updatedCategory = this.categoryRepo.save(cat);

		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> catDtos = categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		return catDtos;
	}

}

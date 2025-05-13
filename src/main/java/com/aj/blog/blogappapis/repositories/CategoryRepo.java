package com.aj.blog.blogappapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aj.blog.blogappapis.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	public boolean existsByCategoryTitle(String categoryTitle);
}

package com.aj.blog.blogappapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.aj.blog.blogappapis.entities.Category;
import com.aj.blog.blogappapis.entities.Post;
import com.aj.blog.blogappapis.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Category> findByCategory(Category category);
}
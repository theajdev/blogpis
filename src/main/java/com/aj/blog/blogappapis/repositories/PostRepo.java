package com.aj.blog.blogappapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.aj.blog.blogappapis.entities.Category;
import com.aj.blog.blogappapis.entities.Post;
import com.aj.blog.blogappapis.entities.User;
import com.aj.blog.blogappapis.payloads.PostDto;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);
    
    List<Post> findByTitleContaining(String title);
}
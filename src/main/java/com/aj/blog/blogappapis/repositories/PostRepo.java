package com.aj.blog.blogappapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import com.aj.blog.blogappapis.entities.Category;
import com.aj.blog.blogappapis.entities.Post;
import com.aj.blog.blogappapis.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);
    
    @Query(value="select p from post where p.title like ?1",nativeQuery = true)
    List<Post> searchByTitle(@Param("key") String title);
}
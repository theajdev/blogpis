package com.aj.blog.blogappapis.services;

import com.aj.blog.blogappapis.entities.Post;
import com.aj.blog.blogappapis.entities.Category;
import com.aj.blog.blogappapis.entities.User;
import com.aj.blog.blogappapis.payloads.PostDto;
import java.util.List;

public interface PostService {

    // Create
    public PostDto createPost(PostDto postDto, int userId, int categoryId);

    // Update
    public PostDto updatePost(PostDto postDto, Integer postId);

    // Delete
    public void deletePost(Integer postId);

    // Get all post
    List<PostDto> getAllPosts();

    // Get Single post
    public PostDto getPostById(Integer postId);

    // Get all posts by category
    public List<PostDto> getPostsByCategory(Category category);

    // Get all posts by user
    public List<PostDto> getPostsByUser(User user);

    // Search Posts
    public List<PostDto> serachPosts(String keyWord);
}

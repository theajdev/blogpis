package com.aj.blog.blogappapis.services;

import com.aj.blog.blogappapis.payloads.PostDto;
import com.aj.blog.blogappapis.payloads.PostResponse;

import java.util.List;

public interface PostService {

    // Create
    public PostDto createPost(PostDto postDto, int userId, int categoryId);

    // Update
    public PostDto updatePost(PostDto postDto, Integer postId,int userId, int categoryId);

    // Delete
    public void deletePost(Integer postId);

    // Get all post
    PostResponse getAllPosts(int pageSize, int pageNumber);

    // Get Single post
    public PostDto getPostById(Integer postId);

    // Get all posts by category
    public List<PostDto> getPostsByCategory(int categoryId);

    // Get all posts by user
    public List<PostDto> getPostsByUser(int userId);

    // Search Posts
    public List<PostDto> serachPosts(String keyWord);
}

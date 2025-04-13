package com.aj.blog.blogappapis.services.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aj.blog.blogappapis.entities.Category;
import com.aj.blog.blogappapis.entities.Post;
import com.aj.blog.blogappapis.entities.User;
import com.aj.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.aj.blog.blogappapis.payloads.PostDto;
import com.aj.blog.blogappapis.repositories.CategoryRepo;
import com.aj.blog.blogappapis.repositories.PostRepo;
import com.aj.blog.blogappapis.repositories.UserRepo;
import com.aj.blog.blogappapis.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto,int userId,int categoryId) {
		
		User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user id", userId));
		Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
		Post post=new Post();
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName("default.png");
		post.setAddedDate(new Date());	
		post.setUser(user);
		post.setCategory(category);
		Post newPost = postRepo.save(post);
		PostDto postToDto = PostToDto(newPost);
		return postToDto;
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PostDto> getAllPosts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PostDto> getPostsByCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PostDto> getPostsByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PostDto> serachPosts(String keyWord) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public PostDto PostToDto(Post post) {
		PostDto postDto = modelMapper.map(post, PostDto.class);
		return postDto;
	}
	
	public Post DtoToPost(PostDto postDto) {
		Post post = modelMapper.map(postDto, Post.class);
		return post;
	}

    
}

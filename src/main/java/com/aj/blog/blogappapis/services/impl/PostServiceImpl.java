package com.aj.blog.blogappapis.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aj.blog.blogappapis.entities.Category;
import com.aj.blog.blogappapis.entities.Post;
import com.aj.blog.blogappapis.entities.User;
import com.aj.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.aj.blog.blogappapis.payloads.PostDto;
import com.aj.blog.blogappapis.payloads.PostResponse;
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
	public PostDto createPost(PostDto postDto, int userId, int categoryId) {

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		Post post = new Post();
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
		
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		Category category = categoryRepo.findById(postDto.getCategory().getCategoryId()).get();
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setCategory(category);
		post.setAddedDate(new Date());
		Post updatedPost = postRepo.save(post);
		PostDto newUpdatedPostDto = PostToDto(updatedPost);
		return newUpdatedPostDto;
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPosts(int pageSize, int pageNumber, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		PageRequest p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pages = postRepo.findAll(p);
		List<Post> allPosts = pages.getContent();
		List<PostDto> list = allPosts.stream().map((posts) -> PostToDto(posts)).collect(Collectors.toList());

		PostResponse postResposne = new PostResponse();
		postResposne.setContent(list);
		postResposne.setPageNumber(pages.getNumber());
		postResposne.setPageSize(pages.getSize());
		postResposne.setTotalElements(pages.getTotalElements());
		postResposne.setTotalPages(pages.getTotalPages());
		postResposne.setLastPage(pages.isLast());
		return postResposne;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		PostDto postToDto = PostToDto(post);
		return postToDto;
	}

	@Override
	public List<PostDto> getPostsByCategory(int categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> posts = postRepo.findByCategory(category);
		List<PostDto> list = posts.stream().map((post) -> PostToDto(post)).collect(Collectors.toList());
		return list;
	}

	@Override
	public List<PostDto> getPostsByUser(int userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		List<Post> usersList = postRepo.findByUser(user);
		List<PostDto> posts = usersList.stream().map((post) -> PostToDto(post)).collect(Collectors.toList());
		return posts;
	}

	@Override
	public List<PostDto> serachPosts(String keyWord) {
		List<Post> titleContaining = postRepo.searchByTitle("%"+keyWord+"%");
		List<PostDto> list = titleContaining.stream().map((title)->PostToDto(title)).collect(Collectors.toList());
		return list;
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

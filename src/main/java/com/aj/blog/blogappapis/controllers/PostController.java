package com.aj.blog.blogappapis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aj.blog.blogappapis.payloads.PostDto;
import com.aj.blog.blogappapis.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	PostService postService;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable int userId, @PathVariable int categoryId){
		PostDto newPost = postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(newPost,HttpStatus.CREATED);
	}

}

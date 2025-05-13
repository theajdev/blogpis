package com.aj.blog.blogappapis.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aj.blog.blogappapis.payloads.ApiResponse;
import com.aj.blog.blogappapis.payloads.PostDto;
import com.aj.blog.blogappapis.payloads.PostResponse;
import com.aj.blog.blogappapis.services.FileService;
import com.aj.blog.blogappapis.services.PostService;
import com.aj.blog.blogappapis.utils.AppConstants;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
public class PostController {

	@Autowired
	PostService postService;

	@Autowired
	FileService fileService;

	@Value("${project.image}")
	private String path;

	// create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable int userId,
			@PathVariable int categoryId) {
		PostDto newPost = postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(newPost, HttpStatus.CREATED);
	}

	// update post by id
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable int postId) {
		PostDto updatePost = postService.updatePost(postDto,postId);
		return ResponseEntity.ok(updatePost);
	}

	// delete post by id
	@DeleteMapping("posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId) {
		postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("post deleted successfully.", true, postId),
				HttpStatus.OK);
	}

	// get posts by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable int userId) {
		List<PostDto> postsByUser = postService.getPostsByUser(userId);

		return new ResponseEntity<List<PostDto>>(postsByUser, HttpStatus.OK);
	}

	// get posts by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable int categoryId) {
		List<PostDto> postsByCategory = postService.getPostsByCategory(categoryId);

		return new ResponseEntity<List<PostDto>>(postsByCategory, HttpStatus.OK);
	}

	// get all posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		PostResponse postResponse = postService.getAllPosts(pageSize, pageNumber, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	// get all posts
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostsById(@PathVariable int postId) {
		PostDto postDto = postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}

	// search posts
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {
		List<PostDto> searchPosts = postService.serachPosts(keywords);
		return new ResponseEntity<List<PostDto>>(searchPosts, HttpStatus.OK);
	}

	// Post Image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@PathVariable int postId, @RequestParam("image") MultipartFile image) throws IOException{
		String fileName = fileService.uploadImage(path, image);
		PostDto postDto = postService.getPostById(postId);
		postDto.setImageName(fileName);
		PostDto updatePost = postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	//Download image
	@GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(org.springframework.http.MediaType.IMAGE_JPEG_VALUE);
		org.springframework.util.StreamUtils.copy(resource, response.getOutputStream());
	}

}

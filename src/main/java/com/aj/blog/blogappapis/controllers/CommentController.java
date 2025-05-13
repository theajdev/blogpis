package com.aj.blog.blogappapis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aj.blog.blogappapis.payloads.ApiResponse;
import com.aj.blog.blogappapis.payloads.CommentDto;
import com.aj.blog.blogappapis.services.CommentService;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/user/{userId}/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComments(@RequestBody CommentDto commentDto, @PathVariable int postId,
			@PathVariable int userId) {
		CommentDto comment = commentService.createComment(commentDto, postId, userId);
		return new ResponseEntity<CommentDto>(comment, HttpStatus.OK);
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComments(@PathVariable int commentId) {
		commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted.", true, commentId), HttpStatus.OK);
	}

}

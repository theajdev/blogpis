package com.aj.blog.blogappapis.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aj.blog.blogappapis.entities.Comment;
import com.aj.blog.blogappapis.entities.Post;
import com.aj.blog.blogappapis.entities.User;
import com.aj.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.aj.blog.blogappapis.payloads.CommentDto;
import com.aj.blog.blogappapis.repositories.CommentRepo;
import com.aj.blog.blogappapis.repositories.PostRepo;
import com.aj.blog.blogappapis.repositories.UserRepo;
import com.aj.blog.blogappapis.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, int postId, int userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment = commentRepo.save(comment);
		return modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(int commentId) {
		Comment comment = commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));
		commentRepo.delete(comment);

	}

}

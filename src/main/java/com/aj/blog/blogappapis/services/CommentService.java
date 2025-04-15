package com.aj.blog.blogappapis.services;

import com.aj.blog.blogappapis.payloads.CommentDto;

public interface CommentService {
	public CommentDto createComment(CommentDto commentDto,int postId);
	public void deleteComment(int commentId);
}

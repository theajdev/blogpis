package com.aj.blog.blogappapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aj.blog.blogappapis.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}

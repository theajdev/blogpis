package com.aj.blog.blogappapis.payloads;

import com.aj.blog.blogappapis.entities.User;

import lombok.Data;

@Data
public class JwtAuthResponse {
	private String token;
	private UserDto user;
}

package com.aj.blog.blogappapis.payloads;

import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InvalidUser {
	private String message;
	private int status;
	private HttpStatusCode statusCode;
	
}

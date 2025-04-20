package com.aj.blog.blogappapis.blog.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Access Denied !!");

	}
	
	protected boolean shouldNotFilter(HttpServletRequest request) throws Exception {
		  String path = request.getRequestURI();

		    return path.startsWith("/api/v1/auth") ||
		           path.startsWith("/v3/api-docs") ||
		           path.startsWith("/swagger-ui") ||
		           path.startsWith("/swagger-resources") ||
		           path.startsWith("/webjars") ||
		           path.startsWith("/configuration");

	}

}

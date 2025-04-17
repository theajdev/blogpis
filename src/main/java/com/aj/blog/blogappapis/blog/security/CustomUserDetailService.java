package com.aj.blog.blogappapis.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aj.blog.blogappapis.entities.User;
import com.aj.blog.blogappapis.repositories.UserRepo;
@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// loading user from database by username 
		User user = userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Username not found: "+username));
		return user;
	}

}

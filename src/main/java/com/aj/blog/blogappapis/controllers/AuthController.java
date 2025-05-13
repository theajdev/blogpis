package com.aj.blog.blogappapis.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aj.blog.blogappapis.blog.security.JWTTokenHelper;
import com.aj.blog.blogappapis.entities.User;
import com.aj.blog.blogappapis.exceptions.InvalidUserException;
import com.aj.blog.blogappapis.payloads.JwtAuthRequest;
import com.aj.blog.blogappapis.payloads.JwtAuthResponse;
import com.aj.blog.blogappapis.payloads.UserDto;
import com.aj.blog.blogappapis.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private JWTTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {
		authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
		String token = jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(this.modelMapper.map((User) userDetails,UserDto.class));
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			throw new InvalidUserException("Invalid Credentials");
		}

	}
	
	//register new user api
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto,@RequestParam(required = false) boolean isAdmin, Authentication auth){
		 if (isAdmin && (auth == null || !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))) {
			 return new ResponseEntity<>("User must be an ADMIN.",HttpStatus.FORBIDDEN);
		    }
		
		UserDto registeredUser = userService.RegisterNewUser(userDto,isAdmin);
		
		return new ResponseEntity<>(registeredUser,HttpStatus.CREATED);
	}
}

package com.aj.blog.blogappapis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aj.blog.blogappapis.entities.Role;
import com.aj.blog.blogappapis.entities.User;
import com.aj.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.aj.blog.blogappapis.payloads.UserDto;
import com.aj.blog.blogappapis.repositories.RoleRepository;
import com.aj.blog.blogappapis.repositories.UserRepo;
import com.aj.blog.blogappapis.services.UserService;
import com.aj.blog.blogappapis.utils.AppConstants;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);

		User savedUser = this.userRepo.save(user);

		return this.UserToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User ID", userId));
		user.setName(userDto.getName());
		// user.setEmail(userDto.getEmail());
		// user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = this.userRepo.save(user);
		UserDto updatedUserDto = this.UserToDto(updatedUser);
		return updatedUserDto;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user Id", userId));
		return this.UserToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> allUsers = this.userRepo.findAll();
		List<UserDto> userDtos = allUsers.stream().map(user -> this.UserToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		userRepo.delete(user);
	}

	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}

	private UserDto UserToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public UserDto RegisterNewUser(UserDto userDto, boolean isAdmin) {
		User user = dtoToUser(userDto);
		// encoded the password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		// roles
		Role role = roleRepository.findById(isAdmin ? AppConstants.ADMIN_USER : AppConstants.NORMAL_USER)
				.orElseThrow(() -> new RuntimeException("Role not found"));
		user.getRoles().add(role);
		User newUser = userRepo.save(user);
		UserDto newUserDto = UserToDto(newUser);

		return newUserDto;
	}
}

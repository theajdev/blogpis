package com.aj.blog.blogappapis.services;

import java.util.List;

import com.aj.blog.blogappapis.payloads.UserDto;

public interface UserService {
	UserDto RegisterNewUser(UserDto userDto,boolean isAdmin);

	UserDto createUser(UserDto userDto);

	UserDto updateUser(UserDto userDto, Integer userId);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllUsers();

	void deleteUser(Integer userId);

}

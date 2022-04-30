package com.blog.api.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.entity.User;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.UserDto;
import com.blog.api.repositories.UserRepo;
import com.blog.api.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user=this.DtoToUser(userDto);
		
	    User savedUser=	this.userRepo.save(user);
		
		return this.userToDto(savedUser);
	}

	
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		User user=this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User udatedUser=this.userRepo.save(user);
		UserDto userDto1=this.userToDto(udatedUser);
		return userDto1;
	}

	
	@Override
	public UserDto getUserById(Integer userId) {

		User user=this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		
		return this.userToDto(user);
	}

	
	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users= this.userRepo.findAll();
		List<UserDto> userDtos= users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	
	@Override
	public void deleteUser(Integer userId) {

		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		this.userRepo.delete(user);
	}

	
	
	public User DtoToUser(UserDto userDto) {
		
		User user=this.modelMapper.map(userDto, User.class);
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		return user;
		
	}
	
	public UserDto userToDto(User user) {
		
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
//		
		return userDto;
		
		
	}
}

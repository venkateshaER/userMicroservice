package com.hashedin.userMicroservice.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hashedin.userMicroservice.dao.User;
import com.hashedin.userMicroservice.dao.UserResponse;
import com.hashedin.userMicroservice.dto.LoginDto;
import com.hashedin.userMicroservice.dto.UserDto;
import com.hashedin.userMicroservice.repository.UserRepositoryInterface;
import com.hashedin.userMicroservice.utility.EncryptUtil;
import com.hashedin.userMicroservice.utility.Response;
import com.hashedin.userMicroservice.utility.ResponseUtil;
import com.hashedin.userMicroservice.utility.TokenUtil;
import com.hashedin.userMicroservice.utility.Utility;
@Service
public class UserService implements UserServiceInterface{
	

	@Autowired
	private UserRepositoryInterface userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EncryptUtil encryptUtil;

	@Override
	public Response userRegister(UserDto userDto) {
	
		
		boolean isUser=userRepository.findByEmailId(userDto.getEmailId()).isPresent();
		
		if(!isUser) {
			User user = modelMapper.map(userDto, User.class);
			user.setPassword(encryptUtil.encryptPassword(user.getPassword()));
			user.setCreatedDate(Utility.todayDate());
			user.setUpdatedDate(Utility.todayDate());
			userRepository.save(user);
			Response reponse=ResponseUtil.getResponse(201, "User Register Successfully ");
			return reponse;
		}
		else {
			Response reponse=ResponseUtil.getResponse(204, "User Already Exits");
			return reponse;
		}
		
	}

	@Override
	public Response userLogin(LoginDto loginDto) {
		boolean isEmail = userRepository.findByEmailId(loginDto.getEmailId()).isPresent();
		if(!isEmail) {
			Response reponse=ResponseUtil.getResponse(204, "Invalid User");
			return reponse;
		}
		else {
			User user = userRepository.findByEmailId(loginDto.getEmailId()).get();
			boolean isPassword = encryptUtil.isPassword(loginDto, user);

			if (!isPassword) {
				Response reponse=ResponseUtil.getResponse(204, "Please check the password");
				return reponse;
			}
			else {
				String token = TokenUtil.generateToken(user.getUserId());
				user.setToken(token);
				user.setUpdatedDate(Utility.todayDate());
				user.setSignIn(true);
				
				userRepository.save(user);
				Response reponse=ResponseUtil.getResponse(201, "Login Successfully");
				return reponse;
				
			}
			
		}
	}

	@Override
	public UserResponse getUser(String token) {
		UserResponse userResponse=new UserResponse();
		Long userid=0L;
		try {
			userid = TokenUtil.verifyToken(token);
		}catch (Exception e) {
			System.out.println(""+e.getMessage());
			Response reponse=ResponseUtil.getResponse(204, "User Not found");
			userResponse.setResponse(reponse);
			return userResponse;
		}
		boolean isUser=userRepository.findById(userid).isPresent();
		if(isUser) {
			User user=userRepository.findById(userid).get();
			if(!user.isSignIn()) {
				Response reponse=ResponseUtil.getResponse(204, "Please SignIn");
				userResponse.setResponse(reponse);
				return userResponse;
			}
			else {
				System.out.println("Uesr Service================"+user);
				userResponse.setUser(user);
				return userResponse;
			}
		
		}
		else {
			Response reponse=ResponseUtil.getResponse(204, "User Not found");
			userResponse.setResponse(reponse);
			return userResponse;
		}
	}

	@Override
	public Response userSignOut(String token) {
	
		Long userid=0L;
		try {
			userid = TokenUtil.verifyToken(token);
		}catch (Exception e) {
			System.out.println(""+e.getMessage());
			Response reponse=ResponseUtil.getResponse(204, "User Not found");
			return reponse;
		}
		boolean isUser=userRepository.findById(userid).isPresent();
		if(isUser) {
			User user=userRepository.findById(userid).get();
			user.setSignIn(false);
			user.setToken("");
			userRepository.save(user);
			Response reponse=ResponseUtil.getResponse(201, "SignOut Successfylly");
			return reponse;
		}
		else {
			Response reponse=ResponseUtil.getResponse(204, "User Not found");
			return reponse;
		}
	}
}

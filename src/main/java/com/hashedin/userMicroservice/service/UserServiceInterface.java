package com.hashedin.userMicroservice.service;


import com.hashedin.userMicroservice.dao.User;
import com.hashedin.userMicroservice.dao.UserResponse;
import com.hashedin.userMicroservice.dto.LoginDto;
import com.hashedin.userMicroservice.dto.UserDto;
import com.hashedin.userMicroservice.utility.Response;


public interface UserServiceInterface {

	Response userRegister(UserDto userDto);

	Response userLogin(LoginDto loginDto);

	UserResponse getUser(String token);

	Response userSignOut(String token);

	User getUserValue(String token);

}

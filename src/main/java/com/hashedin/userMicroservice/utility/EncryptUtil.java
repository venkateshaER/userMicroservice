package com.hashedin.userMicroservice.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hashedin.userMicroservice.dao.User;
import com.hashedin.userMicroservice.dto.LoginDto;



@Component
public class EncryptUtil {
	@Autowired
	private PasswordEncoder PasswordEncoder;

	public String encryptPassword(String password) {
		return PasswordEncoder.encode(password);
	}

	public boolean isPassword(LoginDto loginDto, User user) {

		return PasswordEncoder.matches(loginDto.getPassword(),user.getPassword() );
		
	}


}

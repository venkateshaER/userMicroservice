package com.hashedin.userMicroservice.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hashedin.userMicroservice.dao.User;
import com.hashedin.userMicroservice.dao.UserResponse;
import com.hashedin.userMicroservice.dto.LoginDto;
import com.hashedin.userMicroservice.dto.UserDto;
import com.hashedin.userMicroservice.repository.UserRepositoryInterface;
import com.hashedin.userMicroservice.service.UserServiceInterface;
import com.hashedin.userMicroservice.utility.Response;
import com.hashedin.userMicroservice.utility.Utility;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserServiceInterface userService;
	
	@Autowired
	private UserRepositoryInterface userRepository;
	
	@PostMapping("/register")
	public ResponseEntity<Response> userRegister(@RequestBody UserDto userDto) {
		
		Response response=userService.userRegister(userDto);
		
		return new ResponseEntity<Response>(response,HttpStatus.CREATED);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> userLogin(@RequestBody LoginDto loginDto) {
		
		Response response=userService.userLogin(loginDto);
		
		return new ResponseEntity<Response>(response,HttpStatus.OK);
		
	}
	
	@GetMapping("/getUser/{token}")
	public ResponseEntity<UserResponse> getUser(@PathVariable String token) {
		
		UserResponse user=userService.getUser(token);
		return new ResponseEntity<UserResponse>(user,HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/updatePrice/{price}/{id}")
	public ResponseEntity<User>updatePrice(@PathVariable Long price,@PathVariable Long id){
		
		
			User user=userRepository.findById(id).get();
			user.setUpdatedDate(Utility.todayDate());
			user.setMoneyWallet(price);
			userRepository.save(user);
		
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/signOut")
	public ResponseEntity<Response> userSignOut(@RequestHeader String token) {
		
		Response response=userService.userSignOut(token);
		return new ResponseEntity<Response>(response,HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/getUserValue/{token}")
	public User getUserValue(@PathVariable String token) {
		
		User user=userService.getUserValue(token);
		return user;
		
	}

}

package com.hashedin.userMicroservice.userControllerTest;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.hashedin.userMicroservice.dao.User;
import com.hashedin.userMicroservice.dao.UserResponse;
import com.hashedin.userMicroservice.service.UserService;
import com.hashedin.userMicroservice.userController.UserController;
import com.hashedin.userMicroservice.utility.Response;
import com.hashedin.userMicroservice.utility.ResponseUtil;

@WebMvcTest(UserController.class)
public class UesrControllerTest {
	
	@MockBean
	private UserController userController;
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	
	@Test
	public void getUserbyToken() {
		UserResponse userResponse=new UserResponse();
		Response response= ResponseUtil.getResponse(200, "User Result");
		User user =new User();
		user.setEmailId("venkatesh@email.com");
		user.setMoneyWallet(30000);
		user.setPhone("9876543654");
		user.setUserName("venkatesh");
		userResponse.setResponse(response);
		userResponse.setUser(user);
		
		when(userService.getUser(anyString())).thenReturn(userResponse);
		
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/user/getUser/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ2ZW5rYXRlc2giOjF9.PSXzji0DMxk2hy_SKePMKk_6CIGt2x-Ihd3WVk5UGaM"))
								.andExpect(MockMvcResultMatchers.jsonPath("$.userName", userResponse.getUser().getUserName()).value("venkatesh"))
								.andExpect(status().isAccepted());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

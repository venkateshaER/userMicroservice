package com.hashedin.userMicroservice.dto;

public class UserDto {
	
	private String userName;
	private String emailId;
	private String password;
	private String phone;
	private long moneyWallet;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public long getMoneyWallet() {
		return moneyWallet;
	}
	public void setMoneyWallet(long moneyWallet) {
		this.moneyWallet = moneyWallet;
	}
	@Override
	public String toString() {
		return "UserDto [userName=" + userName + ", emailId=" + emailId + ", password=" + password + ", phone=" + phone
				+ ", moneyWallet=" + moneyWallet + "]";
	}
	
	

}

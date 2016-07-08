package com.microfocus.keystrokedynamics.model;

public class SignInData {

	private String username;
	private String password;
	private String userTimeArray;
	private String pwdTimeArray;
	
	
	
	
	
	public SignInData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SignInData(String username, String password,String userTimeArray,String pwdTimeArray) {
		super();
		this.username = username;
		this.password = password;
		this.userTimeArray = userTimeArray;
		this.pwdTimeArray = pwdTimeArray;
	}
	
	
	
	public String getUserTimeArray() {
		return userTimeArray;
	}
	public void setUserTimeArray(String userTimeArray) {
		this.userTimeArray = userTimeArray;
	}
	public String getPwdTimeArray() {
		return pwdTimeArray;
	}
	public void setPwdTimeArray(String pwdTimeArray) {
		this.pwdTimeArray = pwdTimeArray;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}

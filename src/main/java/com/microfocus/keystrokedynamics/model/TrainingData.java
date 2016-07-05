package com.microfocus.keystrokedynamics.model;

public class TrainingData {

	private String username;
	private String firstTimeArray;
	private String secondTimeArray;
	private String thirdTimeArray;
	private String fourthTimeArray;
	private String fifthTimeArray;
	private String userTimeArray;
	private String pwdTimeArray;
	
	
	public TrainingData(String username, String firstTimeArray, String secondTimeArray, String thirdTimeArray,
			String fourthTimeArray, String fifthTimeArray, String userTimeArray, String pwdTimeArray) {
		super();
		this.username = username;
		this.firstTimeArray = firstTimeArray;
		this.secondTimeArray = secondTimeArray;
		this.thirdTimeArray = thirdTimeArray;
		this.fourthTimeArray = fourthTimeArray;
		this.fifthTimeArray = fifthTimeArray;
		this.userTimeArray = userTimeArray;
		this.pwdTimeArray = pwdTimeArray;
	}
	
	public TrainingData() {
		super();
	}


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstTimeArray() {
		return firstTimeArray;
	}
	public void setFirstTimeArray(String firstTimeArray) {
		this.firstTimeArray = firstTimeArray;
	}
	public String getSecondTimeArray() {
		return secondTimeArray;
	}
	public void setSecondTimeArray(String secondTimeArray) {
		this.secondTimeArray = secondTimeArray;
	}
	public String getThirdTimeArray() {
		return thirdTimeArray;
	}
	public void setThirdTimeArray(String thirdTimeArray) {
		this.thirdTimeArray = thirdTimeArray;
	}
	public String getFourthTimeArray() {
		return fourthTimeArray;
	}
	public void setFourthTimeArray(String fourthTimeArray) {
		this.fourthTimeArray = fourthTimeArray;
	}
	public String getFifthTimeArray() {
		return fifthTimeArray;
	}
	public void setFifthTimeArray(String fifthTimeArray) {
		this.fifthTimeArray = fifthTimeArray;
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
	
	
	
	
}

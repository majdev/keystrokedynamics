package com.microfocus.keystrokedynamics.model;

public class SignUpData {
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String dob;
	private String empid;
	private String firstans;
	private String secondans;
	private String thirdans;
	
	
	public SignUpData() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SignUpData(String firstname, String lastname, String username, String password, String dob, String empid,
			String firstans, String secondans, String thirdans) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.empid = empid;
		this.dob = dob;
		this.firstans = firstans;
		this.secondans = secondans;
		this.thirdans = thirdans;
	}


	public String getFirstname() {
		return firstname;
	}
	
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	
	
	public String getDob() {
		return dob;
	}


	public void setDob(String dob) {
		this.dob = dob;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
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
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getFirstans() {
		return firstans;
	}
	public void setFirstans(String firstans) {
		this.firstans = firstans;
	}
	public String getSecondans() {
		return secondans;
	}
	public void setSecondans(String secondans) {
		this.secondans = secondans;
	}
	public String getThirdans() {
		return thirdans;
	}
	public void setThirdans(String thirdans) {
		this.thirdans = thirdans;
	}

	
}

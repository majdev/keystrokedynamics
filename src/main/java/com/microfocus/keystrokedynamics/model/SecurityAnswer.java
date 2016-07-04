package com.microfocus.keystrokedynamics.model;


public class SecurityAnswer {

	private Long id;
	private String ansOne;
	private String ansTwo;
	private String ansThree;
	
	
	
	public SecurityAnswer(String ansOne, String ansTwo, String ansThree) {
		super();
		this.ansOne = ansOne;
		this.ansTwo = ansTwo;
		this.ansThree = ansThree;
	}
	
	public SecurityAnswer() {
		super();
	}
	
	public SecurityAnswer(Long id,String ansOne, String ansTwo, String ansThree) {
		super();
		this.id=id;
		this.ansOne = ansOne;
		this.ansTwo = ansTwo;
		this.ansThree = ansThree;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAnsOne() {
		return ansOne;
	}
	public void setAnsOne(String ansOne) {
		this.ansOne = ansOne;
	}
	public String getAnsTwo() {
		return ansTwo;
	}
	public void setAnsTwo(String ansTwo) {
		this.ansTwo = ansTwo;
	}
	public String getAnsThree() {
		return ansThree;
	}
	public void setAnsThree(String ansThree) {
		this.ansThree = ansThree;
	}
}

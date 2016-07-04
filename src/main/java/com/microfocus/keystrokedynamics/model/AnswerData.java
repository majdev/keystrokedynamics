package com.microfocus.keystrokedynamics.model;

public class AnswerData {
	private String firstans;
	private String secondans;
	private String thirdans;
	
	
	
	public AnswerData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AnswerData(String firstans, String secondans, String thirdans) {
		super();
		this.firstans = firstans;
		this.secondans = secondans;
		this.thirdans = thirdans;
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

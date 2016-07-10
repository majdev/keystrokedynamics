package com.microfocus.keystrokedynamics.model;

public class DetectionData {

	private String username;
	private String detectionModel;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDetectionModel() {
		return detectionModel;
	}
	public void setDetectionModel(String detectionModel) {
		this.detectionModel = detectionModel;
	}
	public DetectionData(String username, String detectionModel) {
		super();
		this.username = username;
		this.detectionModel = detectionModel;
	}
	public DetectionData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}

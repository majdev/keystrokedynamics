package com.microfocus.keystrokedynamics.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import com.microfocus.keystrokedynamics.model.SignInData;
import com.microfocus.keystrokedynamics.model.SignUpData;
import com.microfocus.keystrokedynamics.model.TrainingData;

public interface DatabaseJDBC {
	
	public Connection connectToDB();
	
	public int createTable(Connection conn, String table,String query);
	
	public <T> int insertData(Connection conn, String table, String query,T pushIndex);
	
	public void selectAll(Connection conn,String table, String query);
	
	public boolean findByUserName(Connection conn, SignUpData signUpData);
	
	public boolean findByUserNamePwd(Connection conn, SignInData signInData);
	
	public int findUserIDByUserName(Connection conn, TrainingData signInData);
	
	public String findPwdByUserID(Connection conn, int id);

	public List<String> findTimeArrayByUserIDnPhrase(Connection conn,String username);
	
	public String findTimingByUserID(Connection conn,String username);
}

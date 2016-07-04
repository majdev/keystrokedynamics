package com.microfocus.keystrokedynamics.controller;

import java.sql.Connection;
import java.sql.ResultSet;

public interface DatabaseJDBC {
	
	public Connection connectToDB();
	
	public int createTable(Connection conn, String table,String query);
	
	public <T> int insertData(Connection conn, String table, String query,T pushIndex);
	
	public void selectAll(Connection conn,String table, String query);

}

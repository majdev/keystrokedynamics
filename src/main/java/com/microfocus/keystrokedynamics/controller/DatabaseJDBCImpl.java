package com.microfocus.keystrokedynamics.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

import org.slf4j.LoggerFactory;

import com.microfocus.keystrokedynamics.App;
import com.microfocus.keystrokedynamics.model.AnswerData;
import com.microfocus.keystrokedynamics.model.SignInData;
import com.microfocus.keystrokedynamics.model.UserData;

public class DatabaseJDBCImpl implements DatabaseJDBC{
	
	private static final Logger logger = Logger.getLogger(App.class);


	public Connection connectToDB() {
		Connection c = null;
	      try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/mydb",
	            "majid", "password");
	      } catch (Exception e) {
	         e.printStackTrace();
	         return null;
	      }
	      logger.info("Opened database successfully");
	      return c;
	}

	public int createTable(Connection conn, String table, String query) {
		try{
			Statement stmt = conn.createStatement();
	        stmt.executeUpdate(query);
	        stmt.close();
	        conn.close();
		}
		catch(SQLException e){
			e.printStackTrace();
			return 500;
		}
		return 201;
	}

	public  <T> int insertData(Connection conn, String table, String query,T pushIndex) {
		conn = connectToDB();
		PreparedStatement pst = null;
		try{
			 pst = null;
	         pst = conn.prepareStatement(query);
	         if(pushIndex instanceof UserData){
		         pst.setString(1, ((UserData) pushIndex).getFirstname());
		         pst.setString(2, ((UserData) pushIndex).getLastname());
		         pst.setString(3, ((UserData) pushIndex).getUsername());
		         pst.setString(4, ((UserData) pushIndex).getPassword());
		         pst.setString(5, ((UserData)pushIndex).getDob());
		         pst.setString(6, ((UserData) pushIndex).getEmpid());

	         }
	         else
	        	 if(pushIndex instanceof AnswerData){
	        		 pst.setString(1, ((AnswerData) pushIndex).getFirstans());
			         pst.setString(2, ((AnswerData) pushIndex).getSecondans());
			         pst.setString(3, ((AnswerData) pushIndex).getThirdans());
	        	 }
	         pst.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
			return 500;
		}
		finally{
			try {
				pst.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 201;
	}
	
	public boolean findByUserNamePwd(Connection conn, SignInData signInData){
		boolean found = false;
		conn = connectToDB();
		PreparedStatement pst = null;
		try{
			pst = conn.prepareStatement("SELECT * FROM "+DBController.USER_TABLE.toUpperCase()+" WHERE USERNAME = '"+signInData.getUsername()+"' AND PASSWORD = '"+signInData.getPassword()+"'");
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				return true;
			return false;
		}
		catch(SQLException e){
			e.printStackTrace();
			logger.info("Error, retrieving password and username");
		}
		return found;
	}

	public void selectAll(Connection conn, String table, String query) {
		conn = connectToDB();
		PreparedStatement pst = null;
		try{
			 pst = conn.prepareStatement("SELECT * FROM COMPANY");
	        ResultSet rs = pst.executeQuery();
	        while(rs.next()){
	        	System.out.println(rs.getString(2));
	        }
	        
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally {
			try{
				pst.close();
				conn.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			
		}
		
	}

}

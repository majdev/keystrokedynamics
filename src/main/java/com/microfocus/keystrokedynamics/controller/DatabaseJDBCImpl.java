package com.microfocus.keystrokedynamics.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import org.slf4j.LoggerFactory;

import com.microfocus.keystrokedynamics.App;
import com.microfocus.keystrokedynamics.constants.Constants;
import com.microfocus.keystrokedynamics.model.AnswerData;
import com.microfocus.keystrokedynamics.model.SignInData;
import com.microfocus.keystrokedynamics.model.SignUpData;
import com.microfocus.keystrokedynamics.model.TrainingDBData;
import com.microfocus.keystrokedynamics.model.TrainingData;
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
	        	 else
	        		 if(pushIndex instanceof TrainingDBData){
	        			 pst.setInt(1, ((TrainingDBData) pushIndex).getUserid());
	    		         pst.setString(2, "Mamihlapinatapai");			//TODO : Hardcode the static string
	    		         pst.setString(3, ((TrainingDBData) pushIndex).getFirstTimeArray());
	    		         pst.setString(4, ".tieRoanl5");			//TODO : Hardcode the static string
	    		         pst.setString(5, ((TrainingDBData) pushIndex).getSecondTimeArray());
	    		         pst.setString(6, "AILUROPHILIAS");			//TODO : Hardcode the static string
	    		         pst.setString(7, ((TrainingDBData) pushIndex).getThirdTimeArray());
	    		         pst.setString(8, "Hi,I'mLazy@12");			//TODO : Hardcode the static string
	    		         pst.setString(9, ((TrainingDBData) pushIndex).getFourthTimeArray());
	    		         pst.setString(10, "ADSCITITIOUSLY");			//TODO : Hardcode the static string
	    		         pst.setString(11, ((TrainingDBData) pushIndex).getFifthTimeArray());
	    		         pst.setString(12, ((TrainingDBData) pushIndex).getUsername());			
	    		         pst.setString(13, ((TrainingDBData) pushIndex).getUserTimeArray());
	    		         pst.setString(14, findPwdByUserID(conn, ((TrainingDBData) pushIndex).getUserid()));			
	    		         pst.setString(15, ((TrainingDBData) pushIndex).getPwdTimeArray());
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
	
	
	
	@Override
	public String findPwdByUserID(Connection conn, int id) {
		conn = connectToDB();
		PreparedStatement pst = null;
		try{
			pst = conn.prepareStatement("SELECT PASSWORD FROM USERINFO WHERE ID = "+id);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				return rs.getString("password");
			return null;
		}
		catch(SQLException e){
			e.printStackTrace();
			logger.info("Error, retrieving password ");
		}
		return null;
	}

	public boolean findByUserNamePwd(Connection conn, SignInData signInData){
		boolean found = false;
		conn = connectToDB();
		PreparedStatement pst = null;
		try{
			pst = conn.prepareStatement("SELECT * FROM "+Constants.USER_TABLE.toUpperCase()+" WHERE USERNAME = '"+signInData.getUsername()+"' AND PASSWORD = '"+signInData.getPassword()+"'");
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
	
	public boolean findByUserName(Connection conn, SignUpData signUpData){
		boolean found = false;
		conn = connectToDB();
		PreparedStatement pst = null;
		try{
			pst = conn.prepareStatement("SELECT * FROM "+Constants.USER_TABLE.toUpperCase()+" WHERE USERNAME = '"+signUpData.getUsername()+"'");
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

	public int findUserIDByUserName(Connection conn, TrainingData trainData) {

		conn = connectToDB();
		PreparedStatement pst = null;
		try{
			pst = conn.prepareStatement("SELECT ID FROM USERINFO WHERE USERNAME = '"+trainData.getUsername()+"'");
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				return rs.getInt("id");
			return -1;
		}
		catch(SQLException e){
			e.printStackTrace();
			logger.info("Error, retrieving password and username");
		}
		return -1;
	}
	
	public int findUserIDByUserName(Connection conn, String username) {

		conn = connectToDB();
		PreparedStatement pst = null;
		try{
			pst = conn.prepareStatement("SELECT ID FROM USERINFO WHERE USERNAME = '"+username+"'");
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				return rs.getInt("id");
			return -1;
		}
		catch(SQLException e){
			e.printStackTrace();
			logger.info("Error, retrieving password and username");
		}
		return -1;
	}


	@Override
	public List<String> findTimeArrayByUserIDnPhrase(Connection conn, String username) {
		List<String> listTimeArray = new ArrayList<String>();
		conn = connectToDB();
		int userid = findUserIDByUserName(conn, username);
		logger.info(userid);
		String pwd = findPwdByUserID(conn, userid);
		logger.info(pwd);
		conn = connectToDB();
		PreparedStatement pst = null;
		try{
			pst = conn.prepareStatement("SELECT PWDTIME FROM TRAININGDATA WHERE USERID = "+userid+" AND PWDPHRASE = '"+pwd+"'");
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				listTimeArray.add(rs.getString("pwdtime"));
			}
			return listTimeArray;
		}
		catch(SQLException e){
			e.printStackTrace();
			logger.info("Error in retrieving time arrays");
		}
		return null;
	}
	
	

}

package com.microfocus.keystrokedynamics;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.microfocus.keystrokedynamics.controller.DBController;
import com.microfocus.keystrokedynamics.controller.DatabaseJDBCImpl;


@SpringBootApplication
public class App 
{
	
	private static final Logger logger = Logger.getLogger(App.class);
    public static void main( String[] args )
    {
    	 SpringApplication.run(App.class, args);  
    	 init();
    }
    
    public static void init(){
    	DatabaseJDBCImpl db = new DatabaseJDBCImpl();
    	Connection conn = db.connectToDB();
    	int status = -1;
    	String userTableCreateQuery = "CREATE TABLE userinfo " +
                "(id SERIAL PRIMARY KEY     NOT NULL," +
                " firstname       CHAR(50)  NOT NULL, " +
                " lastname        CHAR(50)  NOT NULL, " +
                " username        CHAR(50) 	NOT NULL, " +
                " password        CHAR(50)	NOT NULL, " +
                " dob			  CHAR(50)  NOT NULL, "+
                " empid			  CHAR(10)	NOT NULL) ";
    	
    	String securityTableCreateQuery = "CREATE TABLE securityans " +
                "(id SERIAL PRIMARY KEY     NOT NULL," +
                " firstans           CHAR(50)    NOT NULL, " +
                " secondans          CHAR(50)    NOT NULL, " +
                " thirdans           CHAR(50) 	 NOT NULL) ";
    	
    	if(conn!=null){
    		logger.info("Coonection to db created succesfully!!!");
    		status  = db.createTable(conn, DBController.USER_TABLE, userTableCreateQuery);
    		if(status==201){
    			logger.info("UserInfo Table created!!!");
    			status  = db.createTable(db.connectToDB(), DBController.SECURITY_ANS_TABLE, securityTableCreateQuery);
    			if(status ==201)
    				logger.info("Security Answer Table Created");
    			else
    				logger.info("Error creating securityans table!!!");
    		}
    		else
    			logger.info("Error creating Userinfo Table!!!");

    	}
    	else
    		logger.info("Error connecting to database!!!");
    	
    		
    }
}

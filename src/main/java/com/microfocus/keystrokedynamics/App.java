package com.microfocus.keystrokedynamics;

import java.net.NetworkInterface;
import java.sql.Connection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.microfocus.keystrokedynamics.constants.Constants;
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
    	ScheduledExecutorService executor =  Executors.newScheduledThreadPool(1);
    	Function< String, String> shutdownService = (v)->{
    		executor.shutdown();
    		return "";
    	};
    	Runnable createTableTask = ()->{
        	int status = -1;
    		if(conn!=null){
    			logger.info("Coonection to db created succesfully!!!");
        		status  = db.createTable(conn, Constants.USER_TABLE, Constants.userTableCreateQuery);
        		if(status==201){
        			logger.info("UserInfo Table created!!!");
        			status  = db.createTable(db.connectToDB(), Constants.SECURITY_ANS_TABLE, Constants.securityTableCreateQuery);
        			if(status ==201){
        				logger.info("Security Answer Table Created");
            			status  = db.createTable(db.connectToDB(), Constants.TRAINING_DATA_TABLE, Constants.trainingDataTableCreateQuery);
            			if(status ==201){
            				logger.info("Training data table created!!!");
            				status = db.createTable(db.connectToDB(), Constants.TRAINING_OUTPUT_TABLE, Constants.trainingOutputTableCreateQuery);
            				if(status == 201){
            					logger.info("Training output table created");
            					shutdownService.apply("");
            				}
            				else
            					logger.info("Error creating training output table");
            			}
            			else
            				logger.info("Error creating training data table");
        			}
        			else
        				logger.info("Error creating securityans table!!!");
        		}
        		else
        			logger.info("Error creating Userinfo Table!!!");
        	}
        	else
        		logger.info("Error connecting to database!!!");
    	};
    	executor.scheduleAtFixedRate(createTableTask, 0, 5, TimeUnit.MINUTES);
    }
}

package com.microfocus.keystrokedynamics.controller;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microfocus.keystrokedynamics.App;
import com.microfocus.keystrokedynamics.constants.Constants;
import com.microfocus.keystrokedynamics.csv.KeystrokeDataHandlers;
import com.microfocus.keystrokedynamics.model.AnswerData;
import com.microfocus.keystrokedynamics.model.SignInData;
import com.microfocus.keystrokedynamics.model.SignUpData;
import com.microfocus.keystrokedynamics.model.TrainingDBData;
import com.microfocus.keystrokedynamics.model.TrainingData;
import com.microfocus.keystrokedynamics.model.User;
import com.microfocus.keystrokedynamics.model.UserData;
import com.microfocus.keystrokedynamics.pages.KeyParam;
import com.sun.org.apache.xerces.internal.util.HTTPInputSource;

@CrossOrigin(allowedHeaders="Content-Type",origins="*")
@RestController
@RequestMapping("/ksdynamics")
public class DBController {
	private static final Logger logger = Logger.getLogger(App.class);
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value="/signup",method= RequestMethod.POST,consumes = "application/json",produces="application/json")
    public ResponseEntity<String> postSignUpData(@RequestBody SignUpData signUpData) {
    	
    	DatabaseJDBCImpl db = new DatabaseJDBCImpl();
    	Connection conn = db.connectToDB();
    	int status = -1;
    	boolean foundUserName = db.findByUserName(conn, signUpData);
    	if(foundUserName)
    		return new ResponseEntity<String>("{\"Status\": 409}", HttpStatus.OK);
    	UserData userInfo = new UserData(signUpData.getFirstname(), signUpData.getLastname(), signUpData.getUsername(), signUpData.getPassword(),signUpData.getDob(), signUpData.getEmpid());
    	AnswerData answerInfo = new AnswerData(signUpData.getFirstans(), signUpData.getSecondans(), signUpData.getThirdans());
    	status = db.insertData(conn, Constants.USER_TABLE, Constants.USER_INSERT_QUERY,userInfo);
    	if(status == 201){
    		logger.info("UserInfo indexed into user table!!!");
        	status  = db.insertData(conn, Constants.SECURITY_ANS_TABLE, Constants.SEC_ANS_INSERT_QUERY,answerInfo);
        	if(status == 201 )
        		logger.info("Security ANswers indexed into answer table!!!");
        	else
        		logger.info("Error in indexing into answer table!!!");

    	}
    	else
    		logger.info("Error in indexing into iserinfo table!!!");

    	if(status ==201)
        	return new ResponseEntity<String>("{\"Status\":201}",HttpStatus.OK);
    	return new ResponseEntity<String>("{\"Status\":500}",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @RequestMapping(value="/signin",method= RequestMethod.POST,consumes = "application/json",produces="application/json")
    public ResponseEntity<String> postSignInData(@RequestBody SignInData signInData) {   
    	logger.info("Hello you entered sigin endpount");
    	DatabaseJDBCImpl db = new DatabaseJDBCImpl();
    	Connection conn = db.connectToDB();
    	boolean found = false ;
    	found = db.findByUserNamePwd(conn, signInData);
    	if(found){
    		logger.info("Credentials Matched, now Check for Typing METADATA!!!");
    		List<KeyParam> listOfKeyLogs = KeystrokeDataHandlers.parseRawKSData(signInData.getPwdTimeArray());
    		String currentTimeArray = KeystrokeDataHandlers.getHeader(listOfKeyLogs);
    		currentTimeArray+=KeystrokeDataHandlers.getLine(listOfKeyLogs);
    		boolean status = KeystrokeDataHandlers.writeToFile(currentTimeArray);
    		//TODO : check training table whether data is there, if not direct to page for training else do following
    		//TODO: execute R authenticator Script to check for impostor
    		//TODO : if matched , allow else dont
    		if(status)
    			return new ResponseEntity<String>("{\"Found\" :\"true\"}",HttpStatus.OK);
    	}
    	else
    		logger.info("Credentials not matched");
    	return new ResponseEntity<String>("{\"Found\":\"false\"}",HttpStatus.OK);
    }
    
    @RequestMapping(value="/postTrainData",method= RequestMethod.POST,consumes = "application/json",produces="application/json")
    public ResponseEntity<String> postTrainingData(@RequestBody TrainingData trainData) {   
    	int id = -1;
    	int status = -1;
    	DatabaseJDBCImpl db = new DatabaseJDBCImpl();
    	Connection conn = db.connectToDB();
    	id = db.findUserIDByUserName(conn, trainData);
    	if(id !=-1){
    		TrainingDBData indexData = new TrainingDBData(id, trainData.getUsername(), trainData.getFirstTimeArray(), trainData.getSecondTimeArray()
    				, trainData.getThirdTimeArray(), trainData.getFourthTimeArray(), trainData.getFifthTimeArray(), trainData.getUserTimeArray(),
    				trainData.getPwdTimeArray());
    		status = db.insertData(conn, Constants.TRAINING_DATA_TABLE, Constants.TRAIN_DATA_INSERT_QUERY, indexData);
    		if(status == 201){
    			logger.info("Inserted data into training data!!!");
    			return new ResponseEntity<String>("{\"Created\":\"true\"}",HttpStatus.OK);
    		}
    		else
    			logger.info("Error in indexing training data!!!");
    	}
    	else
    		logger.info("Error retrieving user id from username.");
    	return new ResponseEntity<String>("{\"Created\":\"false\"}",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @RequestMapping(value="/trainData",method= RequestMethod.GET,produces="application/json")
    public ResponseEntity<String> trainingData() { 
    	boolean status = false;
    	//TODO : Read data from table parse and convert to file for r call trainer.R script and push the output to db

    	if(status)
        	return new ResponseEntity<String>("{\"Trained\":\"true\"}",HttpStatus.OK);
    	else
        	return new ResponseEntity<String>("{\"Trained\":\"false\"}",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}

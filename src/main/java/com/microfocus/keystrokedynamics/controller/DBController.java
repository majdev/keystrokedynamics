package com.microfocus.keystrokedynamics.controller;

import java.sql.Connection;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microfocus.keystrokedynamics.App;
import com.microfocus.keystrokedynamics.model.AnswerData;
import com.microfocus.keystrokedynamics.model.SignInData;
import com.microfocus.keystrokedynamics.model.SignUpData;
import com.microfocus.keystrokedynamics.model.User;
import com.microfocus.keystrokedynamics.model.UserData;
import com.sun.org.apache.xerces.internal.util.HTTPInputSource;

@RestController
@RequestMapping("/ksdynamics")
public class DBController {
	public static final String USER_TABLE = "userinfo";
	private static final Logger logger = Logger.getLogger(App.class);

	public static final String SECURITY_ANS_TABLE =  "securityans";
	private static final String SEC_ANS_INSERT_QUERY = "INSERT INTO securityans(firstans, secondans, thirdans) VALUES(?,?,?)";
	private static final String USER_INSERT_QUERY = "INSERT INTO userinfo(firstname, lastname, username,password,dob,empid) VALUES(?, ?,?,?,?,?)";

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value="/signup",method= RequestMethod.POST,consumes = "application/json",produces="application/json")
    public ResponseEntity<String> postSignUpData(@RequestBody SignUpData signUpData) {
    	
    	DatabaseJDBCImpl db = new DatabaseJDBCImpl();
    	Connection conn = db.connectToDB();
    	int status = -1;
    	UserData userInfo = new UserData(signUpData.getFirstname(), signUpData.getLastname(), signUpData.getUsername(), signUpData.getPassword(),signUpData.getDob(), signUpData.getEmpid());
    	AnswerData answerInfo = new AnswerData(signUpData.getFirstans(), signUpData.getSecondans(), signUpData.getThirdans());
    	status = db.insertData(conn, USER_TABLE, USER_INSERT_QUERY,userInfo);
    	if(status == 201){
    		logger.info("UserInfo indexed into user table!!!");
        	status  = db.insertData(conn, SECURITY_ANS_TABLE, SEC_ANS_INSERT_QUERY,answerInfo);
        	if(status == 201 )
        		logger.info("Security ANswers indexed into answer table!!!");
        	else
        		logger.info("Error in indexing into answer table!!!");

    	}
    	else
    		logger.info("Error in indexing into iserinfo table!!!");

    	if(status ==201)
        	return new ResponseEntity<String>("{\"Status\":201}",HttpStatus.CREATED);
    	return new ResponseEntity<String>("{\"Status\":500}",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @RequestMapping(value="/signin",method= RequestMethod.POST,consumes = "application/json",produces="application/json")
    public ResponseEntity<String> postSignInData(@RequestBody SignInData signInData) {
    	
    	DatabaseJDBCImpl db = new DatabaseJDBCImpl();
    	Connection conn = db.connectToDB();
    	boolean found = false ;
    	found = db.findByUserNamePwd(conn, signInData);
    	if(found){
    		logger.info("Credentials Matched, now Check for Typing METADATA!!!");
    		//TODO: execute R Script
    		return new ResponseEntity<String>("{\"Found\" :\"true\"}",HttpStatus.OK);
    	}
    	else
    		logger.info("Credentials not matched");
    	return new ResponseEntity<String>("{\"Found\":\"false\"}",HttpStatus.OK);

    	
    }
    
}

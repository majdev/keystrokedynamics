package com.microfocus.keystrokedynamics.constants;

public class Constants {
	public static final String USER_TABLE = "userinfo";
	public static final String SECURITY_ANS_TABLE =  "securityans";
	public static final String TRAINING_DATA_TABLE = "trainingdata";
	public static final String TRAINING_OUTPUT_TABLE = "trainingoutput";
	public static final String SEC_ANS_INSERT_QUERY = "INSERT INTO securityans(firstans, secondans, thirdans) VALUES(?,?,?)";
	public static final String USER_INSERT_QUERY = "INSERT INTO userinfo(firstname, lastname, username,password,dob,empid) VALUES(?, ?,?,?,?,?)" ;

	public static final String TRAIN_DATA_INSERT_QUERY = "INSERT INTO trainingdata(userid, phraseone,timeone,phrasetwo,timetwo,phrasethree" +
		",timethree,phrasefour,timefour,phrasefive,timefive,userphrase,usertime,pwdphrase,pwdtime)" +
		" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;

	
	public static final String userTableCreateQuery = "CREATE TABLE userinfo " +
            "(id SERIAL PRIMARY KEY     NOT NULL," +
            " firstname       CHAR(50)  NOT NULL, " +
            " lastname        CHAR(50)  NOT NULL, " +
            " username        CHAR(50) 	NOT NULL, " +
            " password        CHAR(50)	NOT NULL, " +
            " dob			  CHAR(50)  NOT NULL, "+
            " empid			  CHAR(10)	NOT NULL) ";
	
	public static final String securityTableCreateQuery = "CREATE TABLE securityans " +
            "(id SERIAL PRIMARY KEY     NOT NULL," +
            " firstans           CHAR(50)    NOT NULL, " +
            " secondans          CHAR(50)    NOT NULL, " +
            " thirdans           CHAR(50) 	 NOT NULL) ";
	
	public static final String trainingDataTableCreateQuery = "CREATE TABLE trainingdata "+
			"(userid			 	 INTEGER 	 NOT NULL," +
			" phraseone			     CHAR(50)	 NOT NULL," +
			" timeone			 	 CHAR(5000)	 NOT NULL," +
			" phrasetwo			 	 CHAR(50)	 NOT NULL," +
			" timetwo			 	 CHAR(5000)	 NOT NULL," +
			" phrasethree			 CHAR(50)	 NOT NULL," +
			" timethree			 	 CHAR(5000)	 NOT NULL," +
			" phrasefour			 CHAR(50)	 NOT NULL," +
			" timefour			 	 CHAR(5000)	 NOT NULL," +
			" phrasefive			 CHAR(50)	 NOT NULL," +
			" timefive		 	 	 CHAR(5000)	 NOT NULL," +
			" userphrase			 CHAR(50)	 NOT NULL," +
			" usertime		 	 	 CHAR(5000)	 NOT NULL," +
			" pwdphrase			 	 CHAR(50)	 NOT NULL," +
			" pwdtime		 	 	 CHAR(5000)	 NOT NULL) ";

	public static final String trainingOutputTableCreateQuery = "CREATE TABLE trainingoutput "+
			"(userid			 	 INTEGER 	 NOT NULL," +
			" phraseone			     CHAR(50)	 NOT NULL," +
			" outputone			 	 CHAR(5000)	 NOT NULL," +
			" phrasetwo			 	 CHAR(50)	 NOT NULL," +
			" outputtwo			 	 CHAR(5000)	 NOT NULL," +
			" phrasethree			 CHAR(50)	 NOT NULL," +
			" outputthree			 CHAR(5000)	 NOT NULL," +
			" phrasefour			 CHAR(50)	 NOT NULL," +
			" outputfour			 CHAR(5000)	 NOT NULL," +
			" phrasefive			 CHAR(50)	 NOT NULL," +
			" outputfive		 	 CHAR(5000)	 NOT NULL," +
			" userphrase			 CHAR(50)	 NOT NULL," +
			" useroutput		 	 CHAR(5000)	 NOT NULL," +
			" pwdphrase			 	 CHAR(50)	 NOT NULL," +
			" pwdoutput		 	 	 CHAR(5000)	 NOT NULL) ";

}

package com.microfocus.keystrokedynamics.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.microfocus.keystrokedynamics.App;
import com.microfocus.keystrokedynamics.pages.KeyLogs;
import com.microfocus.keystrokedynamics.pages.KeyParam;


public class KeystrokeDataHandlers {
	
	private static final Logger logger = Logger.getLogger(App.class);

	private static final double MILLI_TO_SECS = 1.0/1000.0;
	
	public static ArrayList<KeyParam> parseRawKSData(String timingArray){
		ArrayList<KeyParam> listOfKeyLogs = new ArrayList<KeyParam>();
		String keyLogs[] = timingArray.split(" ");
		for(String keyLog : keyLogs){
			keyLog = keyLog.trim();
			String ksParams[] = keyLog.split(",");
			KeyParam obj = new KeyParam();
			int keyCode =0;
			for(int i = 0;i<ksParams.length;i++){
				switch(i){
					case 0 :  keyCode = Integer.parseInt(ksParams[i].trim());
							  break;
					
					case 1:	  obj.setKeydown(Double.parseDouble(ksParams[i].trim()));
							  break;
							  
					case 2:	  obj.setKeyup(Double.parseDouble(ksParams[i].trim()));
							  break;
				}
			}
			if(keyCode == 16)		//SHIFT
				obj.setKeyCode("SHIFT");
			else
				if(keyCode == 13)
					obj.setKeyCode("ENTER");
				else
					if(keyCode == 20)
						obj.setKeyCode("CAPSLOCK");
					else
						if(keyCode ==32)
							obj.setKeyCode("SPACE");
						else
							obj.setKeyCode(Character.toString((char)keyCode));
			obj.setHeld(obj.getKeyup()-obj.getKeydown());
			listOfKeyLogs.add(obj);
		}
		return listOfKeyLogs;
	}
	
	public static String getHeader(List<KeyParam> listOfKeyLogs,boolean train){
		String header = "";
		if(train)
			header+="repetition,";
		header += "hold["+listOfKeyLogs.get(0).getKeyCode()+"]";
		String prevCode = listOfKeyLogs.get(0).getKeyCode();
		int listSize = listOfKeyLogs.size();
		for(int i =1;i<listSize;i++){
			header += ","+"keydown["+listOfKeyLogs.get(i).getKeyCode()+"] - keydown["+prevCode+"],keydown["+listOfKeyLogs.get(i).getKeyCode()+"] - "+
						"keyup["+prevCode+"],hold["+listOfKeyLogs.get(i).getKeyCode()+"]";
			prevCode = listOfKeyLogs.get(i).getKeyCode();
		}
		header+="\n";
		logger.info(header);
		return header;
	}
	
	public static String getLine(List<KeyParam> listOfKeyLogs,int reps){
		String line = "";
		
		if(reps>=0)
			line +=reps+",";
		line += MILLI_TO_SECS * listOfKeyLogs.get(0).getHeld();
		int listSize = listOfKeyLogs.size();
		for(int i = 1;i<listSize;i++){
			double ddTime = MILLI_TO_SECS * (listOfKeyLogs.get(i).getKeydown()-listOfKeyLogs.get(i-1).getKeydown());
			double duTime = MILLI_TO_SECS * (listOfKeyLogs.get(i).getKeydown()-listOfKeyLogs.get(i-1).getKeyup());
			double heldTime = MILLI_TO_SECS * listOfKeyLogs.get(i).getHeld();
			heldTime=heldTime<0?0.0:heldTime;
			
			line+=","+ddTime+","+duTime+","+heldTime;
		}
		line+="\n";
		logger.info(line);
		return line;
	}
	
	public static boolean writeToFile(String content,String filepath){
		logger.info(content);
		File file = new File(filepath);
		try {
			
		// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			} 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}

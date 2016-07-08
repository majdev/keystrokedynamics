package com.microfocus.keystrokedynamics.csv;

import com.microfocus.keystrokedynamics.model.SignInData;

/**
 * Created by CKhadija on 8/7/2016.
 */
public class SignInDataCSV {
    String filePath = "SignInCSV.csv";

    public void createCSVFile(SignInData signInData) {
        String userTimeArray = signInData.getUserTimeArray();
        String pwdTimeArray = signInData.getPwdTimeArray();
        writeToFile(userTimeArray);
    }

    private void writeToFile(String userTimeArray) {
        String keys[] = userTimeArray.split(" ");
        for (int i = 0; i < keys.length; i++) {
            String valuesInString[] = keys[i].split(",");
            float values[] = new float[3];
            for(int j=1;j<3;j++)
            {
                values[j] = Float.parseFloat(valuesInString[j]);
            }
        }
    }
}
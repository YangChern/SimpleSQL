package com.gzu.simpleSQL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by YangChern on 2016/12/5.
 */
public class InsertData {
    boolean judgeError = true;



    public InsertData() {

    }

    public InsertData(String tableName) {
        try {
            String data = " This content will append to the end of the file";

            File file = new File("E:\\" + tableName + ".csv");

            //if file doesnt exists, then create it
            if (!file.exists()) {
                System.out.println("Table dosen't exist!");
            } else {
                //true = append file
                FileWriter fileWritter = new FileWriter(file.getName(), true);
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                bufferWritter.write(data);
                bufferWritter.write(data);
                bufferWritter.close();
                System.out.println("Done");
            }
        } catch (IOException e) {
            setStatus(false);
            e.printStackTrace();
        }
    }

    public void setStatus(boolean judgeError) {
        this.judgeError = judgeError;
    }

    public boolean getStatus() {
        return this.judgeError;
    }

}

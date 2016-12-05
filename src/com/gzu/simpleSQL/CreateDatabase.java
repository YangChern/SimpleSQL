package com.gzu.simpleSQL;

import java.io.*;

/**
 * Created by 陈杨 on 2016/12/5.
 */
public class CreateDatabase {
    boolean judgeError = true;

    public CreateDatabase() {

    }

    public CreateDatabase(String dbName) {
        try {
            File file = new File("E:\\" + dbName + ".db");
            //if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            setStatus(false);
            e.printStackTrace();
        }
    }

    public void insertTable(String tableName) {
        try {
            File file = new File("E:\\default.db");
            //if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            //true = append file
            FileWriter fileWritter = new FileWriter(file.getName(), true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(tableName + "\n");
            bufferWritter.close();
//            System.out.println("Done");
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

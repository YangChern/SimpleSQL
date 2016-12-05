package com.gzu.simpleSQL;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by YangChern on 2016/12/5.
 */
public class CreateTable {
    boolean judgeError = true;

    public CreateTable() {

    }

    public CreateTable(String tableName, Map<String,String> map) {
        try {
//            String data = " This content will append to the end of the file";

            File file = new File("E:\\" + tableName + ".csv");

            //if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            } else {
                System.out.println("The Table has existed!");
            }
            FileWriter fileWritter = new FileWriter(file.getName(), true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            Iterator it=map.keySet().iterator();
            boolean flg = false;
            while(it.hasNext()){
//                String key;
                String value;
                value=map.get(it.next().toString());
                if(flg) {
                    bufferWritter.write(",");
                }
                bufferWritter.write(value);
                flg = true;
//                System.out.println(key+"--"+value);
            }
            bufferWritter.write("\n");
            bufferWritter.close();

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

    public void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }


//    public static void main(String[] args) {
//        try {
//            String data = " This content will append to the end of the file\n";
//
//            File file = new File("E:\\table.csv");
//
//            //if file doesnt exists, then create it
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
//            //true = append file
//            FileWriter fileWritter = new FileWriter(file.getName(), true);
//            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
//            bufferWritter.write(data);
//            bufferWritter.write(data);
//            bufferWritter.close();
//
//            System.out.println("Done");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
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
            /**首先将表信息写入default.db
             *写入格式：_TABLE_NAME,tableName
             *          columnName1,columnType1
             *          columnName2,columnType2
             *             ......  ,   ......
             *             ......  ,   ......
             */
            File filedb = new File("default.db");
            if (!filedb.exists()) {
                filedb.createNewFile();
                FileWriter fileWritter = new FileWriter(filedb.getName(), true);
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                Iterator it = map.keySet().iterator();
                boolean flg = false;
                bufferWritter.write("_TABLE_NAME," + tableName + "\n");
                while(it.hasNext()){
                    String key;
                    String value;
                    key = it.next().toString();
                    value = map.get(key);
                    if(flg) {
                        bufferWritter.write(",");
                    }
                    bufferWritter.write(value);
                    flg = true;
//                System.out.println(key+"--"+value);
                }
                bufferWritter.write("\n");
                bufferWritter.close();
            }

            /** 创建tableName.csv文件
             *  将数据库列名写入文件
             *  格式：columnName1,columnName2,columnName3,...,...(末尾无逗号或其他字符)
             */
            File file = new File(tableName + ".csv");
            if (!file.exists()) {
                file.createNewFile();
                FileWriter fileWritter = new FileWriter(file.getName(), true);
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                Iterator it = map.keySet().iterator();
                boolean flg = false;
                while(it.hasNext()){
                    String value;
                    value = map.get(it.next().toString());
                    if(flg) {
                        bufferWritter.write(",");
                    }
                    bufferWritter.write(value);
                    flg = true;
                }
                bufferWritter.write("\n");
                bufferWritter.close();
            } else {//将表文件是否存在作为数据库表存在的依据，也可以读取default.db文件的数据库表的信息来判断。
                System.out.println("The Table has existed!");
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
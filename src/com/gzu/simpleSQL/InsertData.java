package com.gzu.simpleSQL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by YangChern on 2016/12/5.
 */
public class InsertData {
    boolean judgeError = true;

    public InsertData() {
    }

    public InsertData(String tableName, ArrayList al) {
        try {

            File file = new File(tableName + ".csv");
            if (!file.exists()) {
                System.out.println("Table dosen't exist!");
                this.setStatus(false);
            } else {
                file.createNewFile();                                                   //是不是创建新文件就把以前的信息覆盖掉了?？
                FileWriter fileWritter = new FileWriter(file.getName(), true);
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                boolean flg = false;
                Iterator it = al.iterator();
                while (it.hasNext()){
                    Object obj = it.next();
                    if(flg) {
                        bufferWritter.write(",");
                    }
                    bufferWritter.write(obj.toString());
//                    System.out.println("obj:" + obj.toString());
                }
                bufferWritter.write("\n");
                bufferWritter.close();
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

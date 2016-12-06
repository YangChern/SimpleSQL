package com.gzu.simpleSQL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by YangChern on 2016/12/6.
 */

//select tname1.age, tname2.birth from tname1,     tname2 where tname.id = tname2.id order by tname1.id

//select tname1.age,tname2.birth
//from tname1,tname2
//where tname.id=tname2.id
//      and
//      tname.id>1
//order by tname1.age

public class Select {
    public Select() {
    }

    public Select(List<String> selParam, List<String> fromParam, List<String> whereParam, String orderParam, int[] keyIndex) {
//        ArrayList <int[]> a = new ArrayList <int[]>();
//        List<String> infoList = new ArrayList<String>();
        Iterator it = selParam.iterator();
        String[] selColumn = new String[20];
        int i = 0;
//        Map<String, String> selMap = new HashMap<String, String>();
        while (it.hasNext()){
            Object obj = it.next();
            String[] infoLine = new String[2];
            infoLine = obj.toString().split(".");
//            selMap.put(infoLine[0], infoLine[1]);
            selColumn[i++] = infoLine[0];
            selColumn[i++] = infoLine[1];
        }

        i = 0;
        it = selParam.iterator();
        String[] fromTableName = new String[20];
        while (it.hasNext()){
            Object obj = it.next();
            fromTableName[i++] = obj.toString();
        }

        i = 0;
        it = selParam.iterator();
        String[] condition = new String[20];
        while (it.hasNext()){
            Object obj = it.next();
            String[] infoLine = new String[2];
            infoLine = obj.toString().split("=|>|<|>=|<=|<>");
//            selMap.put(infoLine[0], infoLine[1]);
            condition[i++] = infoLine[0];
            condition[i++] = infoLine[1];
        }
    }

    public void readFileByLines(String tableName) {
        File file = new File(tableName);
        BufferedReader reader = null;
        try {
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
}

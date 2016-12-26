package com.gzu.simpleSQL;

import org.junit.Test;

import java.util.*;

/**
 * Created by YangChern on 2016/12/26.
 */
public class Join {
    @Test
    public void Test1() {
//        int i = 0;
        Table1 tb1 = new Table1();
        List<String> columnValue1 = new ArrayList<String>();
        List<String> columnName1 = new ArrayList<String>();
        int NameFlg = 0;
        ArrayList<String[]> ans1 = new ArrayList<String[]>();
        String[] tmp = new String[50];

        columnName1 = tb1.getColumnName();
        columnValue1 = tb1.getColumnValue();
        Iterator it = columnName1.iterator();
        int j = 0;
        while(it.hasNext()) {
            tmp[j++] = it.next().toString();
            if(j % columnName1.size() == 0) {
                ans1.add(tmp);
                j = 0;
                tmp = new String[50];
            }
        }

        j = 0;
        tmp = new String[50];
        it = columnValue1.iterator();
        while(it.hasNext()) {
            tmp[j] = it.next().toString();
            j = j + 1;
            if(j % columnName1.size() == 0) {
                ans1.add(tmp);
                j = 0;
                tmp = new String[50];
            }
        }

//        for(int i = 0; i < columnName1.size(); i++) {
//            System.out.println(ans1.get(1)[i]);
//        }

        int lineCount = columnValue1.size()/columnName1.size();
        for(int i = 0; i < lineCount+1; i++){                   //ans1中第一行是列名
            for(int k = 0; k < columnName1.size(); k++) {
                System.out.print(ans1.get(i)[k] + "||");
            }
            System.out.println();
        }

//        ArrayList<String[]> ansTmp1 = new ArrayList<String[]>();

        Table2 tb2 = new Table2();
        List<String> columnValue2 = new ArrayList<String>();
        List<String> columnName2 = new ArrayList<String>();
        ArrayList<String[]> ans2 = new ArrayList<String[]>();
        tmp = new String[50];

        columnName2 = tb2.getColumnName();
        columnValue2 = tb2.getColumnValue();
        it = columnName2.iterator();
        j = 0;
        while(it.hasNext()) {
            tmp[j++] = it.next().toString();
            if(j % columnName2.size() == 0) {
                ans2.add(tmp);
                j = 0;
                tmp = new String[50];
            }
        }

        j = 0;
        tmp = new String[50];
        it = columnValue2.iterator();
        while(it.hasNext()) {
            tmp[j] = it.next().toString();
            j = j + 1;
            if(j % columnName2.size() == 0) {
                ans2.add(tmp);
                j = 0;
                tmp = new String[50];
            }
        }

//        for(int i = 0; i < columnName1.size(); i++) {
//            System.out.println(ans.get(1)[i]);
//        }

        int lineCount2 = columnValue2.size()/columnName2.size();
        for(int i = 0; i < lineCount2+1; i++){                   //ans中第一行是列名
            for(int k = 0; k < columnName2.size(); k++) {
                System.out.print(ans2.get(i)[k] + "||");
            }
            System.out.println();
        }

    }
}

class Table1{
    private List<String> columnValue = new ArrayList<String>();
    private List<String> columnName = new ArrayList<String>();

    public Table1(){
        columnName.add("Sid");
        columnName.add("Sname");
        columnName.add("Sage");
        columnName.add("Ssex");
        columnValue.add("1001");
        columnValue.add("AAA");
        columnValue.add("18");
        columnValue.add("M");
        columnValue.add("1002");
        columnValue.add("BBB");
        columnValue.add("19");
        columnValue.add("F");
        columnValue.add("1003");
        columnValue.add("CCC");
        columnValue.add("20");
        columnValue.add("M");
    }


    public List<String> getColumnValue() {
        return columnValue;
    }

    public List<String> getColumnName() {
        return columnName;
    }

}

class Table2{
    private List<String> columnValue = new ArrayList<String>();
    private List<String> columnName = new ArrayList<String>();

    public Table2(){
        columnName.add("Sid");
        columnName.add("Sgrade");

        columnValue.add("1001");
        columnValue.add("89.5");
        columnValue.add("1001");
        columnValue.add("89.5");
        columnValue.add("1001");
        columnValue.add("77.5");
        columnValue.add("1002");
        columnValue.add("99");
        columnValue.add("1002");
        columnValue.add("95");
        columnValue.add("1002");
        columnValue.add("66");
        columnValue.add("1002");
        columnValue.add("89");
        columnValue.add("1003");
        columnValue.add("90");
    }


    public List<String> getColumnValue() {
        return columnValue;
    }

    public List<String> getColumnName() {
        return columnName;
    }

}
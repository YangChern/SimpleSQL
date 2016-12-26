package com.gzu.simpleSQL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YangChern on 2016/12/5.
 */
class Parser {

    private boolean judgeError = false;
    private String Smt = new String();
    private String[] strArray = new String[50];
    private String tableName = "";

    public Parser() {

    }


    public void setSmt(String Smt) {
        this.Smt = Smt;
        this.characterProcessing();
    }
    public String getSmt() {
        return Smt;
    }
    public void getStrArray() {
        for(int i = 0; i < strArray.length; i++) {
            System.out.println(strArray[i]);
        }
    }

//create database dbname;
//create table tname1{id int, age int};
//create table tname1{id int, birth int};

//insert into tname1 values(1, 20);
//insert into tname2 values(2, 20261201);

//select tname1.age, tname2.birth from tname1,     tname2 where tname.id = tname2.id order by tname1.id

    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }
    public void characterProcessing() {
        String StrBlank = this.getSmt();
        StrBlank.trim();
        for(int i = 1; i < StrBlank.length(); ) {
            char[] c = new char[50];
            c = StrBlank.toCharArray();
            if(c[i] == ' ') {
                if (!(((c[i - 1] >= '0' && c[i - 1] <= '9') || (c[i - 1] >= 'a' && c[i - 1] <= 'z') || (c[i - 1] == '*')) && ((c[i + 1] >= '0' && c[i + 1] <= '9') || (c[i + 1] >= 'a' && c[i + 1] <= 'z') || (c[i + 1] == '*')))) {
                    StrBlank = removeCharAt(StrBlank, i);
                    continue;
                }
            }
            i++;
        }
//        StrBlank1 = this.getSmt().replaceAll("(?:\\w \\w)", "\\w-\\w");
//        StrBlank2 = StrBlank1.replaceAll(" ", "");
        System.out.println("StrBlank= " + StrBlank);
        String[] newStrArray = new String[50];
        newStrArray = StrBlank.split(", | |,|;|\\(|\\)|\\{|\\}");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < newStrArray.length; i++) {
            if(newStrArray[i] != null&&newStrArray[i] !=" ") {
                list.add(newStrArray[i]);
            }
        }
        this.strArray =  list.toArray(new String[1]);
        if(strArray[0].compareToIgnoreCase("create") == 0) {                    //create
            if(strArray[1].compareToIgnoreCase("database") == 0) {
//                CreateDatabase CreateDB = new CreateDatabase(strArray[2]);
                CreateDatabase CreateDB = new CreateDatabase("default");
                System.out.println("Creating database succeed!");
            } else if(strArray[1].compareToIgnoreCase("table") == 0) {
                tableName = strArray[2];
                Map<String,String> map = new HashMap<String,String>();
                for(int i = 3; i < strArray.length-1; i += 2) {
                    map.put(strArray[i], strArray[i+1]);
                }
                CreateTable ct = new CreateTable(tableName, map);
                if(ct.getStatus()) {
                    System.out.println("Creating table has finished!");
                } else {
                    System.out.println("Creating table failed!");
                }
            } else {
                judgeError = true;
            }
        } else if(strArray[0].compareToIgnoreCase("insert") == 0) {             //insert into
            if(strArray[1].compareToIgnoreCase("into") == 0 && strArray[3].compareToIgnoreCase("values") == 0) {
                tableName = strArray[2];
                ArrayList al = new ArrayList();
                for(int i = 4; i < strArray.length; i++) {
                    al.add(strArray[i]);
                }
                InsertData idata = new InsertData(tableName, al);
                if(idata.getStatus()) {
                    System.out.println("Inserting data succeed!");
                }
            } else {
                judgeError = true;
            }
        } else if(strArray[0].compareToIgnoreCase("select") == 0) {             //select tname1.age,tname2.birth
                                                                                //from tname1,tname2
                                                                                //where tname.id=tname2.id and tname.id>1
                                                                                //order by tname1.age
            List<String> selParam = new ArrayList<String>();
            List<String> fromParam = new ArrayList<String>();
            List<String> whereParam = new ArrayList<String>();
            String orderParam = "";
            int[] keyIndex = new int[]{0, 0, 0};
            int j = 0;
            for(int i = 1; i < strArray.length; i++) {
                if(strArray[i].compareToIgnoreCase("from") == 0) {          //keyIndex = {fromPosition, wherePosition, orderbyPosition}
                    keyIndex[j++] = i;
                } else if(strArray[i].compareToIgnoreCase("where") == 0) {
                    keyIndex[j++] = i;
                } else if(strArray[i].compareToIgnoreCase("order") == 0 && strArray[i+1].compareToIgnoreCase("by") == 0) {
                    keyIndex[j++] = i;
                }
            }
//            //?
//            for(int i = 0; i < strArray.length; i++) {
//                System.out.println("i = " + i + " : " + strArray[i]);
//            }

            for(int i = 1; i < keyIndex[0]; i++) {
                selParam.add(strArray[i]);
            }
            if(selParam.isEmpty()) {//select 后面无参数
                System.out.print("No select parameter. ");
                judgeError = true;
            } else {
                //获取from后面的参数
                if (keyIndex[1] > 0) {//有where条件
                    for (int i = keyIndex[0]; i < keyIndex[1]; i++) {
                        fromParam.add(strArray[i]);
                    }
                } else if(keyIndex[2] > 0) {//无where，有order by
                    for (int i = keyIndex[0]; i < keyIndex[2]; i++) {
                        fromParam.add(strArray[i]);
                    }
                } else if(keyIndex[1] == 0 && keyIndex[2] == 0) {//无where，无order by
                    for (int i = keyIndex[0]; i < strArray.length; i++) {
                        fromParam.add(strArray[i]);
                    }
                }

                //获取where后面的参数
                if (keyIndex[1] > 0) { //有where关键字才有意义
                    if (keyIndex[2] > 0) {//有order by  select tname1.age,tname2.birth from tname1,tname2 where tname.id=tname2.id and tname.id>1 order by tname1.age
                        for (int i = keyIndex[1]; i < keyIndex[2]; i++) {
                            whereParam.add(strArray[i]);
                        }
                    } else {                //select tname1.age,tname2.birth from tname1,tname2 where tname.id=tname2.id and tname.id>1
                        for (int i = keyIndex[1]; i < strArray.length; i++) {
                            whereParam.add(strArray[i]);
                        }
                    }
                }

                //获取order by参数
                if(keyIndex[2] >0) {
                    orderParam = strArray[strArray.length-1];
                }
//                Select slt = new Select(selParam, fromParam, whereParam, orderParam, keyIndex);
//            System.out.println("Should Select");
            }
        } else {
            judgeError = true;
        }
        if(judgeError) {
            System.out.println("Syntax error!");
        }
    }
}


package com.gzu.simpleSQL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Created by YangChern on 2016/12/5.
 */

public class MainTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String smt;
        System.out.print("SimpleSQL>");
        while(in.hasNext()) {
            String Smt = in.nextLine();
            if(Smt.compareToIgnoreCase("exit") == 0) {
                System.out.println("Bye!");
                break;
            }
            Parser pser = new Parser();
            pser.setSmt(Smt);
            pser.getStrArray();
            System.out.print("SimpleSQL>");
        }
    }
}

class Parser {
    boolean judgeError = false;
    String Smt = new String();
    String[] strArray = new String[50];
    int j = 0;
    int k = 0;
    String tableName = "";

    public void setSmt(String Smt) {
        this.Smt = Smt;
        this.characterProcessing();
    }
    public String getSmt() {
        return Smt;
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
                if (!(((c[i - 1] >= '0' && c[i - 1] <= '9') || (c[i - 1] >= 'a' && c[i - 1] <= 'z')) && ((c[i + 1] >= '0' && c[i + 1] <= '9') || (c[i + 1] >= 'a' && c[i + 1] <= 'z')))) {
                    StrBlank = removeCharAt(StrBlank, i);
                    continue;
                }
            }
            i++;
        }
//        StrBlank1 = this.getSmt().replaceAll("(?:\\w \\w)", "\\w-\\w");
//        StrBlank2 = StrBlank1.replaceAll(" ", "");
        System.out.println("StrBlank + " + StrBlank);
        String[] newStrArray = new String[50];
        newStrArray = StrBlank.split(", | |,|;|\\(|\\)");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < newStrArray.length; i++) {
            if(newStrArray[i] != null&&newStrArray[i] !=" ") {
                list.add(newStrArray[i]);
            }
        }
        this.strArray =  list.toArray(new String[1]);
        if(strArray[0].compareToIgnoreCase("create") == 0) {
            if(strArray[1].compareToIgnoreCase("database") == 0) {
                System.out.println("Should Create Database");
            } else if(strArray[1].compareToIgnoreCase("table") == 0) {
                tableName = strArray[2];
                System.out.println("Should Create Table");
            } else {
                judgeError = true;
            }
        } else if(strArray[0].compareToIgnoreCase("insert") == 0) {
            if(strArray[1].compareToIgnoreCase("into") == 0) {
                System.out.println("Should Insert Into");
            } else {
                judgeError = true;
            }
        } else if(strArray[0].compareToIgnoreCase("select") == 0) {
            System.out.println("Should Select");
        } else {
            judgeError = true;
        }
        if(judgeError) {
            System.out.println("Syntax error!");
        }
    }

    public void getStrArray() {
        for(int i = 0; i < strArray.length; i++) {
            System.out.println(strArray[i]);
        }
    }
}

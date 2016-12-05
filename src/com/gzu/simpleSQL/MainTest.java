package com.gzu.simpleSQL;
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



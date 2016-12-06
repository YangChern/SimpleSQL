package com.gzu.simpleSQL;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by YangChern on 2016/12/5.
 */
public class ShowTable {
    private static ArrayList<String> filelist = new ArrayList<String>();
    public static void main(String[] args) throws Exception {

        String filePath = "E://";
        getFiles(filePath);
    }
    /*
     * 通过递归得到某一路径下所有的目录及其文件
     */
    public static ArrayList<String> getFiles(String filePath){
        File root = new File(filePath);
        File[] files = root.listFiles();
        for(File file:files){
            if(!file.isDirectory()){
                String str = "";
                str = file.getName().replaceAll(".csv", "");
                System.out.println(str);
                filelist.add(str);
            }
        }
        return filelist;
    }
}

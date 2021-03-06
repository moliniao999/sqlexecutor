package com.lm.util;

import com.lm.exception.SqlExecutorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 13:35
 **/

public class SqlFileReader {

    private static Logger log = LoggerFactory.getLogger(SqlFileReader.class);


    /***
     * 获取指定目录下的所有的文件（不包括文件夹）
     * @param obj
     * @return
     */
    public static List<File> getListFiles(Object obj) {
        File directory = null;
        if (obj instanceof File) {
            directory = (File) obj;
        } else {
            directory = new File(obj.toString());
        }
        List<File> files = new ArrayList<File>();
        if (directory.isFile()&&(directory.getName().endsWith("txt")||directory.getName().endsWith("sql"))) {
            files.add(directory);
            return files;
        } else if (directory.isDirectory()) {
            File[] fileArr = directory.listFiles();
            for (int i = 0; i < fileArr.length; i++) {
                File fileOne = fileArr[i];
                files.addAll(getListFiles(fileOne));
            }
        }
        return files;
    }


    /**
     * 读取sql文件内容
     * @param filePath
     * @return
     */
    public static List<String> readFileIntoList(String filePath) {
        List<String> list = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    list.add(lineTxt);
                }
                bufferedReader.close();
                read.close();
            } else {
                log.info("找不到指定的文件");
            }
        } catch (Exception e) {
            log.info("读取文件内容错误",e);
            throw new SqlExecutorException("读取文件内容错误");
        }
        return list;
    }

}

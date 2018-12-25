package com.qst.dbutils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Properties p = null;
    static {
        try{
            p = new Properties();
            p.load(new FileInputStream("config/mysql.properties"));
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
//    获取对应的键
    public static String getValue(String key){
        return p.get(key).toString();
    }
}

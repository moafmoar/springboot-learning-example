package com.full.cn.utils;

import java.util.Properties;

/**
 * Created by full on 2017/5/26.
 */
public class PropertiesUtils {
    private static Properties properties=new Properties();

    static{
        try {
            //注意属性配置文件所在的路径
            properties.load(PropertiesUtils.class.getClassLoader().getResourceAsStream("solr.properties"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String readProperty(String property){
        return (String) properties.get(property);
    }
}

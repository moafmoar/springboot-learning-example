package com.full.cn.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {
    public static Map<String,String> json2map(String jsonObject){
        Map<String, String> map = new HashMap<String, String>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            map = mapper.readValue(jsonObject, new TypeReference<HashMap<String,String>>(){});
            System.out.println(map);
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @Test
    public void StringvalueOf(){

    }
}

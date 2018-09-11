package com.full.cn.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StringCount {

    private HashMap map;
    private int counter;
    public StringCount() {
        map = new HashMap<String,Integer>();
    }
    public void hashInsert(String string) {
        if (map.containsKey(string)) {   //判断指定的Key是否存在
            counter = (Integer)map.get(string);  //根据key取得value
            map.put(string, ++counter);
        } else {
            map.put(string, 1);
        }
    }
    public HashMap getHashMap(){
        return map;
    }


    public static void main(String []args){
        String [] parts = {"宝马X5/dese","宝马X5/dese","奥迪A8"};
        StringCount stringCount = new StringCount();

        for (String s:parts) {
            stringCount.hashInsert(s);
        }
        Map map = stringCount.getHashMap();
        Iterator it = map.keySet().iterator();
        String temp;
        while (it.hasNext()) {
            temp = (String)it.next();
            System.out.println(temp+"出现了"+map.get(temp)+"次");
        }
        System.out.println(map);

    }
}

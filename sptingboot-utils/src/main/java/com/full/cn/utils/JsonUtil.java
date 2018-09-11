package com.full.cn.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * Created by full on 2017/6/14.
 */
public class JsonUtil {
    public static String xml2jsonString(String xml)throws JSONException {
        JSONObject xmlJSONObj = XML.toJSONObject(xml);
        return xmlJSONObj.toString();
    }

    public static JSONObject xml2jsonObject(String xml)throws JSONException {
        JSONObject xmlJSONObj = XML.toJSONObject(xml);
        return xmlJSONObj;
    }
}

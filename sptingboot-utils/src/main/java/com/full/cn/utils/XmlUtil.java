package com.full.cn.utils;


import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;

public class XmlUtil {

    /**
     * @description 将xml字符串转换成map
     * @param xml
     * @return Map
     */
    public static List<Map<String,String>> readStringXmlOut(String xml) {
        List<List<Map<String,String>>> processInfoList = new ArrayList<>();
        List<Map<String,String>> ActivityList = new ArrayList<>();  //Activity //节点
        List<Map<String,String>> TransitionSetList = new ArrayList<>(); //TransitionSet 节点
        Map<String,String> formCtrlMap = new HashMap(); //formCtrlMap


        org.dom4j.Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            org.dom4j.Element rootElt = doc.getRootElement(); // 获取根节点
            System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
            Iterator iter = rootElt.elementIterator("ActivitieSet"); // 获取根节点下的子节点ActivitieSet


            // 遍历ActivitieSet节点
            while (iter.hasNext()) {
                Element itemEle = (Element) iter.next();
                Iterator iter2 = itemEle.elementIterator("Activity"); //获取流程节点Activity
                while (iter2.hasNext()){
                    Map<String,String> Activitymap = new HashMap(); //  Activity //节点
                    Element recordEle = (Element) iter2.next();

                    Iterator ActionSetIter = recordEle.elementIterator("ActionSet"); //获取ActionSet
                    while (ActionSetIter.hasNext()){

                        Element actionSetEle = (Element) ActionSetIter.next();
                        Iterator actionIter = actionSetEle.elementIterator("Action"); //获取Action
                        while (actionIter.hasNext()){
                            Element actionEle = (Element) actionIter.next();
                            String actiionsetValue = actionEle.attributeValue("value");
                            Activitymap.put("actiionsetValue",actiionsetValue);
                        }

                    }

                    String activityCode = recordEle.attributeValue("activityCode");
                    String activityName = recordEle.attributeValue("activityName");
                    System.out.println("activityCode:" + activityCode);
                    System.out.println("activityName:" + activityName);
                    Activitymap.put("activityCode", activityCode);
                    Activitymap.put("activityName", activityName);
                    //newMap.put("Activitymap",)
                    ActivityList.add(Activitymap);


                }
                processInfoList.add(ActivityList);
            }

            //获取FormCtrl
            /*Iterator formCtrlIter = recordEle.elementIterator("FormCtrl");
            while(formCtrlIter.hasNext()){
                Element formCtrlEle = (Element) ActionSetIter.next();
                String fileName = formCtrlEle.attributeValue("id");
                String value = formCtrlEle.attributeValue("right");
                formCtrlMap.put("fileName",fileName);
                formCtrlMap.put("value",value);
            }*/

            Iterator TransitionSet = rootElt.elementIterator("TransitionSet");
            while (TransitionSet.hasNext()) {
                Element recordEless = (Element) TransitionSet.next();
                Iterator iter2 = recordEless.elementIterator("Transition");
                while (iter2.hasNext()) {
                    Map<String,String> TransitionSetmap= new HashMap(); // TransitionSet 关系
                    Element itemEle = (Element) iter2.next();
                    String from = itemEle.attributeValue("from");
                    String to = itemEle.attributeValue("to");
                    System.out.println(from);
                    System.out.println(to);
                    TransitionSetmap.put("from",from);
                    TransitionSetmap.put("to",to);
                    TransitionSetList.add(TransitionSetmap);

                }
                processInfoList.add(TransitionSetList);
            }

            for (Map m:TransitionSetList) {
                for (Map actm:ActivityList) {
                    if (m.get("to").equals(actm.get("activityCode"))||m.get("from").equals(actm.get("activityCode"))){
                        m.put("activityName",actm.get("activityName"));
                        m.put("actiionsetValue",actm.get("actiionsetValue"));
                    }
                }

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TransitionSetList;
    }


    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        org.apache.commons.beanutils.BeanUtils.populate(obj, map);

        return obj;
    }



    public static String ClobToString(Clob clob) throws SQLException, IOException {

        String reString = "";
        java.io.Reader is = clob.getCharacterStream();// 得到流
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            sb.append(s);
            s = br.readLine();
        }
        reString = sb.toString();
        return reString;
    }

    public static Clob oracleStr2Clob(String str, Clob lob) throws Exception {
        Method methodToInvoke = lob.getClass().getMethod(
                "getCharacterOutputStream", (Class[]) null);
        Writer writer = (Writer) methodToInvoke.invoke(lob, (Object[]) null);
        writer.write(str);
        writer.close();


        return lob;
    }

}

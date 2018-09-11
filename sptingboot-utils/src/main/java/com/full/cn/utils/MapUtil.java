package com.full.cn.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class MapUtil {
    /**
     * @author moafmoar
     * @date 2017-11-11
     * @desc 将map对象转换成javabean
     * @param javaBean
     * @param map
     * @param <T>
     * @return
     */
    @SuppressWarnings({ "rawtypes","unchecked", "hiding" })
    public static <T> T map2Java(T javaBean, Map map) {
        try {
            // 获取javaBean属性
            BeanInfo beanInfo = Introspector.getBeanInfo(javaBean.getClass());
            // 创建 JavaBean 对象
            Object obj = javaBean.getClass().newInstance();

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            if (propertyDescriptors != null && propertyDescriptors.length > 0) {
                String propertyName = null; // javaBean属性名
                Object propertyValue = null; // javaBean属性值
                for (PropertyDescriptor pd : propertyDescriptors) {
                    propertyName = pd.getName();
                    if ("processXml".equals(propertyName)&&map.get(propertyName)!= null) {
                        pd.getWriteMethod().invoke(obj, new Object[] { null });
                    }else{
                        if (map.containsKey(propertyName)) {
                            propertyValue = map.get(propertyName);
                            pd.getWriteMethod().invoke(obj, new Object[] { propertyValue });
                        }
                    }

                }
                return (T) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

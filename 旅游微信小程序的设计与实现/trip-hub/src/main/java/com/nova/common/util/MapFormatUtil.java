package com.nova.common.util;

import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MapFormatUtil {

    /**
     * 将Map中的key由下划线转换为驼峰
     */
    public static Map formatHumpName(Map map) {

        if (CollectionUtils.isEmpty(map)) {
            return null;
        }
        Map newMap = new HashMap<>();
        map.forEach((k, v) -> newMap.put(toFormatCol(k.toString()), v));
        return newMap;
    }

    public static String toFormatCol(String colName) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, colName);
    }

    /**
     * 将List中map的key值命名方式格式化为驼峰
     */
    public static List<Map> formatHumpNameForList(List<Map> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Map> newList = new ArrayList<>();
        for (Map o : list) {
            newList.add(formatHumpName(o));
        }
        return newList;
    }

    public static  <T> T convertMapToJavaBean(Class<T> clazz, Map<String, Object> params) {
        T obj = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            obj = clazz.newInstance();
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String propertyName = propertyDescriptor.getName();
                if (params.containsKey(propertyName)) {
                    Object value = params.get(propertyName);
                    if ("".equals(value)) {
                        value = null;
                    }
                    Object args[] = new Object[1];
                    args[0] = value;
                    propertyDescriptor.getWriteMethod().invoke(obj, args);
                }
            }
        } catch (Exception e) {
            log.error("map转换异常",e);
        }
        return obj;
    }
}

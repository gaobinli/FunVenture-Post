package com.nova.common.util;

import com.alibaba.fastjson.JSON;
import com.nova.common.exception.AppException;
import com.nova.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
public class MtofBeanUtil {

    /**
     * @param object 要强转的对象 , entityClass 强转后的类型
     * @return T
     * @descprition 对象类型强转
     */
    public static <T> T convertBean(Object object, Class<T> entityClass) {
        if (null == object) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(object), entityClass);
    }

    /**
     * @param object 要转话的对象
     * @return java.util.Map<?, ?>
     * @descprition 对象转为map
     */
    public static Map<?, ?> objectToMap(Object object) {
        return convertBean(object, Map.class);
    }


    /**
     * @param map map集合, t 对象
     * @return T
     * @descprition map转换对象
     */
    public static <T> T mapToObject(Map map, Class<T> t) {
        T instance = null;
        try {
            //移除value 为 null
            removeNullValue(map);
            instance = t.newInstance();
            map.forEach((k, v) -> {
                if (v != null && v instanceof Timestamp) {
                    map.put(k, Timestamp.valueOf(v.toString()).toLocalDateTime());
                }
                if (v != null && v instanceof Date) {
                    map.put(k, ((Date) v).toLocalDate());
                }
            });

            org.apache.commons.beanutils.BeanUtils.populate(instance, map);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AppException(e.getMessage(), ErrorCode.other("500"));
        }

        return instance;
    }

    public static void removeNullValue(Map map) {
        Set set = map.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
            Object obj = iterator.next();
            Object value = map.get(obj);
            if (value == null) {
                iterator.remove();
            }
        }
    }


    /**
     * list<map>转 list<bean>
     *
     * @param maps
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> mapsToObjects(List<Map> maps, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(maps)) {
            Map map = null;
            for (int i = 0, size = maps.size(); i < size; i++) {
                list.add(mapToObject(maps.get(i), clazz));
            }
        }
        return list;
    }

    /**
     * 将List<T>转换为List<Map>
     *
     * @param objList
     * @return
     */
    public static <T> List<Map> objectsToMaps(List<T> objList) {
        List<Map> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(objList)) {
            objList.forEach(obj -> {
                list.add(objectToMap(obj));
            });
        }
        return list;

    }

    /**
     * @param source 资源对象, target 目标对象, ignoreProperties 赋值new String[]{}
     * @return T  target对象
     * @descprition 对象转换
     */
    public static <T> T copy(Object source, Class<T> target, String... ignoreProperties) {
        T targetInstance = null;
        try {
            targetInstance = target.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ArrayUtils.isEmpty(ignoreProperties)) {
            assert targetInstance != null;
            BeanUtils.copyProperties(source, targetInstance);
        } else {
            assert targetInstance != null;
            BeanUtils.copyProperties(source, targetInstance, ignoreProperties);
        }
        return targetInstance;

    }


    /**
     * @param list, target, ignoreProperties]
     * @return java.util.List<T>
     * @descprition 对象list转换
     */
    public static <T, E> List<T> copyList(List<E> list, Class<T> target, String... ignoreProperties) {
        List<T> targetList = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return targetList;
        }
        for (E e : list) {
            targetList.add(copy(e, target, ignoreProperties));
        }
        return targetList;
    }
}

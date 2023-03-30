package com.shy.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author shy
 * @date 2023-03-27
 */
public class BeanUtil {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    public static <T> T mapToBean(Class<T> clz, Map map) {

        T t = BeanUtils.instantiateClass(clz);

        List<Field> fields = getFields(clz);
        fields.forEach(field -> {
            String fieldName = field.getName();
            Method setter = BeanUtils.getPropertyDescriptor(clz, fieldName).getWriteMethod();
            Object value = map.get(fieldName);
            try {
                if (setter != null) {
                    setter.invoke(t, parseValue(value,field.getType()));
                }

            } catch (IllegalAccessException e) {
                logger.warn("bean组装失败.IllegalAccess fieldName:{}, value:{}",fieldName,value);
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                logger.warn("bean组装失败.InvocationTarget fieldName:{}, value:{}",fieldName,value);
                e.printStackTrace();
            } catch (IllegalArgumentException e){
                logger.warn("bean组装失败.IllegalArgument fieldName:{}, value:{}, class:{} -> {}",fieldName,value, value.getClass(),field.getType());
                e.printStackTrace();
            }
        });
        return t;
    }

    public static List<Field> getFields(Class clz) {
        List<Field> fields = new ArrayList<>();
        //获取所有属性(包含父类)
        fields = getParentField(clz, fields);
        return fields;
    }


    /**
     * 递归获取所有父类的属性
     *
     * @param calzz
     * @param list
     * @return add by liuyulei 2019-02-14
     */
    private static List<Field> getParentField(Class<?> calzz, List<Field> list) {

        if (calzz.getSuperclass() != Object.class) {
            getParentField(calzz.getSuperclass(), list);
        }

        Field[] fields = calzz.getDeclaredFields();
        list.addAll(arrayConvertList(fields));

        return list;
    }


    /**
     * 将数组转换成List
     *
     * @param fields
     * @return add by liuyulei 2019-02-14
     */
    private static List<Field> arrayConvertList(Field[] fields) {
        List<Field> resultList = new ArrayList<>(fields.length);
        Collections.addAll(resultList, fields);
        return resultList;

    }

    private static Object parseValue(Object value,Class type){
        if(value == null){
            return null;
        }
        //基本类型映射,防止出现Integer & Long类型转换不匹配
        if(type == Long.class){
            return Long.valueOf(value.toString());
        } else if(type == Integer.class){
            return Integer.valueOf(value.toString());
        } else if(type == Short.class){
            return Short.valueOf(value.toString());
        } else if(type == Float.class){
            return Float.valueOf(value.toString());
        } else if (type == Double.class) {
            return Double.valueOf(value.toString());
        } else if(type == Character.class){
            return value.toString().charAt(0);
        } else if (type == Boolean.class) {
            return Boolean.valueOf(value.toString());
        }
        return value;
    }
}

package com.gsralex.gflow.core.util;


import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author gsralex
 * @date 2018/2/20
 */
public class PropertiesUtils {


    public static <T> T getConfig(String path, Class<T> type) throws IllegalAccessException, IOException, InstantiationException {
        InputStream is = PropertiesUtils.class.getResourceAsStream(path);
        return getConfig(is, type);
    }

    public static <T> T getConfig(InputStream is, Class<T> type) throws IOException, IllegalAccessException, InstantiationException {
        Properties prop = new Properties();
        prop.load(is);
        T instance = type.newInstance();

        Map<String, Field> annotationMap = getAnnotaionMap(type);
        for (Map.Entry<Object, Object> entry : prop.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (annotationMap.containsKey(key)) {
                Field field = annotationMap.get(key);
                Object typeValue= convertValue(field,value);
                try {
                    Method method = type.getMethod(getSetMethodName(field.getName()), field.getType());
                    method.invoke(instance, typeValue);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }
        return instance;
    }

    private static Map<String, Field> getAnnotaionMap(Class type) {
        Map<String, Field> result = new HashMap<>();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            PropertyName propName = field.getAnnotation(PropertyName.class);
            if (propName != null) {
                result.put(StringUtils.lowerCase(propName.name()), field);
            }
        }
        return result;
    }

    private static Object convertValue(Field field, String strValue) {
        Class type = field.getType();
        Object value;
        if (type == String.class) {
            value = strValue;
        } else if (type == int.class || type == Integer.class) {
            value = Integer.parseInt(strValue);
        } else if (type == float.class || type == Float.class) {
            value = Float.parseFloat(strValue);
        } else if (type == double.class || type == Double.class) {
            value = Double.parseDouble(strValue);
        } else if (type == long.class || type == Long.class) {
            value = Long.parseLong(strValue);
        } else if (type == boolean.class || type == Boolean.class) {
            value = Boolean.parseBoolean(strValue);
        } else if (type == byte.class || type == Byte.class) {
            value = Byte.parseByte(strValue);
        } else if (type == short.class || type == Short.class) {
            value = Short.parseShort(strValue);
        } else {
            value = strValue;
        }
        return value;
    }

    private static String getSetMethodName(String fieldName) {
        return "set" + getPostMethodName(fieldName);
    }

    private static String getPostMethodName(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}

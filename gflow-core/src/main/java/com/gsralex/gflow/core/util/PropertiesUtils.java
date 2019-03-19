package com.gsralex.gflow.core.util;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author gsralex
 * @date 2018/2/20
 */
public class PropertiesUtils {

    private PropertiesUtils() {
    }

    public static Properties getProperties(String path) throws IOException {
        InputStream is = PropertiesUtils.class.getResourceAsStream(path);
        Properties properties = new Properties();
        properties.load(is);
        return properties;
    }

    public static <T> T getConfig(String path, Class<T> type) throws IOException {
        InputStream is = PropertiesUtils.class.getResourceAsStream(path);
        return getConfig(is, type);
    }

    public static <T> T getConfig(InputStream is, Class<T> type) throws IOException {
        Properties props = new Properties();
        props.load(is);
        return getConfig(props, type);
    }

    public static <T> T getConfig(Properties props, Class<T> type) throws IOException {
        T instance = null;
        try {
            instance = type.newInstance();
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }
        Map<String, Field> annotationMap = getAnnotaionMap(type);
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (annotationMap.containsKey(key)) {
                Field field = annotationMap.get(key);
                Object typeValue = convertValue(field, value);
                try {
                    Method method = type.getMethod(getSetMethodName(field.getName()), field.getType());
                    method.invoke(instance, typeValue);
                } catch (NoSuchMethodException e) {
                } catch (InvocationTargetException e) {
                } catch (IllegalAccessException e) {
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
                result.put(propName.name().toLowerCase(), field);
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

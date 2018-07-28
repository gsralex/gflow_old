package com.gsralex.gflow.scheduler.util;


import com.gsralex.gdata.bean.exception.DataException;
import com.gsralex.gdata.bean.mapper.FieldValue;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Properties;

/**
 * @author gsralex
 * @date 2018/2/20
 */
public class PropertiesUtils {

    public static <T> T getConfig(String filePath, Class<T> type) throws IOException, IllegalAccessException, InstantiationException {
        Properties prop = new Properties();
        InputStream is = new BufferedInputStream(new FileInputStream(new File(filePath)));
        prop.load(is);


        T instance = type.newInstance();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            PropertyName propertyName = field.getAnnotation(PropertyName.class);
            if (propertyName != null) {
                String propName = propertyName.name();
                if (StringUtils.isEmpty(propName)) {
                    propName = field.getName();
                }
                try {
                    Method method=type.getMethod(getSetMethodName(field.getName()),field.getType());
                    method.invoke(instance,getValue(prop,propName,field.getType()));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return instance;
    }

    private static Object getValue(Properties prop,String propName,Class type){
        Object value;
        String strValue = prop.getProperty(propName);
        if(type==String.class){
            value=strValue;
        }else if(type==int.class || type==Integer.class){
            value=Integer.parseInt(strValue);
        }else if(type==float.class || type==Float.class){
            value=Float.parseFloat(strValue);
        }else if(type==double.class || type==Double.class) {
            value=Double.parseDouble(strValue);
        }else if(type==long.class || type==Long.class){
            value=Long.parseLong(strValue);
        }else if(type==boolean.class || type==Boolean.class){
            value=Boolean.parseBoolean(strValue);
        }else if(type==byte.class|| type==Byte.class){
            value=Byte.parseByte(strValue);
        }else if(type==short.class || type==Short.class){
            value=Short.parseShort(strValue);
        }else{
            value=strValue;
        }
        return value;
    }

    private static String getSetMethodName(String fieldName) {
        return "set" + getPostMethodName(fieldName);
    }

    private static String getPostMethodName(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static void main(String[] args) throws IllegalAccessException, IOException, InstantiationException {

        String filePath = PropertiesUtils.class.getResource("/gflow.properties").getPath();
//        GFlowConfig config = PropertiesUtils.getConfig(filePath, GFlowConfig.class);
//        System.out.println(config);
    }
}

package com.gsralex.gflow.scheduler.util;


import com.gsralex.gflow.core.config.GFlowConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.lang.reflect.Field;
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
        Field[] fields = type.getFields();
        for (Field field : fields) {
            PropertyName propertyName = field.getAnnotation(PropertyName.class);
            if (propertyName != null) {
                String typeName = field.getType().getTypeName();
                String propName = propertyName.name();
                if (StringUtils.isEmpty(propName)) {
                    propName = field.getName();
                }
                Object value = null;
                String strValue = prop.getProperty(propName);
                switch (typeName) {
                    case "java.lang.String": {
                        value = strValue;
                        break;
                    }
                    case "int": {
                        value = NumberUtils.toInt(strValue);
                        break;
                    }
                    case "long": {
                        value = NumberUtils.toLong(strValue);
                        break;
                    }
                    case "double": {
                        value = NumberUtils.toDouble(strValue);
                        break;
                    }
                    case "float": {
                        value = NumberUtils.toFloat(strValue);
                        break;
                    }
                    case "boolean": {
                        value = Boolean.valueOf(strValue);
                        break;
                    }
                }
                field.set(instance, value);
            }
        }
        return instance;
    }

    public static void main(String[] args) throws IllegalAccessException, IOException, InstantiationException {

        String filePath = PropertiesUtils.class.getResource("/gflow.properties").getPath();
        GFlowConfig config = PropertiesUtils.getConfig(filePath, GFlowConfig.class);
        System.out.println(config);
    }
}

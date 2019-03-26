package com.gsralex.gflow.core.rpc.server;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gsralex
 * @version 2019/3/26
 */
public class ServiceCache {
    private Map<String, Object> serviceHandles = new HashMap<>();
    private Map<String, Map<String, List<Method>>> serviceMethods = new HashMap<>();

    public void registerHandler(Class clazz, Object impl) {
        serviceHandles.put(clazz.getName(), impl);
        Map<String, List<Method>> sameNameMethods = new HashMap<>();
        serviceMethods.put(clazz.getName(), sameNameMethods);
        for (Method method : clazz.getMethods()) {
            if (sameNameMethods.containsKey(method.getName())) {
                sameNameMethods.get(method.getName()).add(method);
            } else {
                List<Method> methods = new ArrayList<>();
                methods.add(method);
                sameNameMethods.put(method.getName(), methods);
            }
        }
    }

    public boolean containsHandler(String interfaceName) {
        return serviceHandles.containsKey(interfaceName);
    }

    public Object getHandler(String interfaceName) {
        return serviceHandles.get(interfaceName);
    }

    public List<Method> getMethods(String interfaceName, String methodName) {
        return serviceMethods.get(interfaceName).get(methodName);
    }

}

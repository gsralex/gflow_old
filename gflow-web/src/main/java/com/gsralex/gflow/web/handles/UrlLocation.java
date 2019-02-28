package com.gsralex.gflow.web.handles;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gsralex
 * @version 2019/2/26
 */
public class UrlLocation {

    private Map<String, HttpHandler> httpHandlerMap = new HashMap<>();

    public HttpHandler getHttpHandle(String path) {
        return httpHandlerMap.get(path.toLowerCase());
    }

    public void addHttpHandler(HttpHandler handler) {
        httpHandlerMap.put(handler.getRequestPath().toLowerCase(), handler);
    }

}

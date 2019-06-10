package com.gsralex.gflow.scheduler;

import java.util.HashMap;

/**
 * @author gsralex
 * @version 2019/3/13
 */
public class ArgsTool {
    private HashMap<String, String> argMaps = new HashMap<>();

    private void put(String key, String value) {
        argMaps.put(key, value);
    }

    public static ArgsTool fromArgs(String[] args) {
        ArgsTool argsTool = new ArgsTool();
        for (String arg : args) {
            if (arg.startsWith("--")) {

            }
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                String key = args[i].substring(2, args[i].length()).toLowerCase();
                String value = "";
                if (i + 1 < args.length) {
                    value = args[i + 1];
                }
                argsTool.put(key, value);
            }
        }
        return argsTool;
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(argMaps.get(key));
    }

    public int getInt(String key) {
        return Integer.parseInt(argMaps.get(key));
    }

    public String getString(String key) {
        return argMaps.get(key);
    }
}

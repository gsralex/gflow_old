package com.gsralex.gflow.scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/13
 */
public class MainArgs {

    private List<String> argList = new ArrayList<>();

    public MainArgs(String[] args) {
        this.argList = Arrays.asList(args);
    }

    public boolean isMaster() {
        return argList.contains("--master");
    }
}

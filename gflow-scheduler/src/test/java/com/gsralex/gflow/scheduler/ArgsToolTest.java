package com.gsralex.gflow.scheduler;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author gsralex
 * @version 2019/5/29
 */
public class ArgsToolTest {
    @Test
    public void getString() throws Exception {
        String[] args = new String[]{"--master", "true"};
        ArgsTool argsTool = ArgsTool.fromArgs(args);
        Assert.assertEquals(argsTool.getString("master"), "true");
    }

    @Test
    public void getBoolean() {
        String[] args = new String[]{"--master", "true"};
        ArgsTool argsTool = ArgsTool.fromArgs(args);
        Assert.assertEquals(argsTool.getBoolean("master"), true);
    }

    @Test
    public void getInt() {
        String[] args = new String[]{"--master", "1"};
        ArgsTool argsTool = ArgsTool.fromArgs(args);
        Assert.assertEquals(argsTool.getInt("master"), 1);
    }
}
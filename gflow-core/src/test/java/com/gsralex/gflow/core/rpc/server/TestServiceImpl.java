package com.gsralex.gflow.core.rpc.server;

/**
 * @author gsralex
 * @version 2019/3/16
 */
public class TestServiceImpl implements TestService {

    @Override
    public int foo0(Integer i) {
        return 0;
    }

    @Override
    public int foo0(Integer i, String d) {
        return 0;
    }

    @Override
    public int foo1(Integer i, String d) {
        return 1;
    }
}

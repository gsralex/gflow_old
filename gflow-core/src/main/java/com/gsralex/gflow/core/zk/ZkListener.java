package com.gsralex.gflow.core.zk;

/**
 * @author gsralex
 * @version 2018/8/20
 */
public interface ZkListener<T> {

    void subscribeListen(T value);
}

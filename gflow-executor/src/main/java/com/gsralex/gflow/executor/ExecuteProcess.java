package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.Parameter;

/**
 * @author gsralex
 * @version 2018/8/4
 */
public interface ExecuteProcess {

    boolean process(long id, Parameter parameter);
}

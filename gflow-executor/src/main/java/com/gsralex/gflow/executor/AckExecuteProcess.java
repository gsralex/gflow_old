package com.gsralex.gflow.executor;

import com.gsralex.gflow.core.context.Parameter;

/**
 * @author gsralex
 * @version 2018/8/30
 */
public interface AckExecuteProcess {

    void process(long id, Parameter parameter);
}

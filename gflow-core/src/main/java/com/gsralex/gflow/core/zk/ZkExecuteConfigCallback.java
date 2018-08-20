package com.gsralex.gflow.core.zk;

import com.gsralex.gflow.core.model.ExecuteConfig;

/**
 * @author gsralex
 * @version 2018/8/20
 */
public interface ZkExecuteConfigCallback {

    void callback(long id, ExecuteConfig config);
}

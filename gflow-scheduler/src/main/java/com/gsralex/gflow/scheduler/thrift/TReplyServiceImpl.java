package com.gsralex.gflow.scheduler.thrift;

import com.gsralex.gflow.core.thrift.TJobDesc;
import com.gsralex.gflow.core.thrift.TJobResult;
import com.gsralex.gflow.core.thrift.TReplyServie;
import org.apache.thrift.TException;

/**
 * @author gsralex
 * @date 2018/2/18
 */
public class TReplyServiceImpl implements TReplyServie.Iface {
    @Override
    public TJobResult reply(TJobDesc desc, TJobResult result) throws TException {
        return null;
    }
}

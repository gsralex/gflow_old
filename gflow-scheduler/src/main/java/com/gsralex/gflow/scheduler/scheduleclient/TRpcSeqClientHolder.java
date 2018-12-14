package com.gsralex.gflow.scheduler.scheduleclient;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.thriftgen.TJobDesc;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.server.ScheduleTransportException;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gsralex
 * @version 2018/12/13
 */
public class TRpcSeqClientHolder {

    private static final Logger LOGGER = Logger.getLogger(TRpcSeqClientHolder.class);
    private static final int RETRY_CNT = 2;
    private static Integer lastIIndex = 0;
    private TRpcClient rpcClient;


    public void seqSchedule(TJobDesc jobDesc) {
//        List<IpAddress> ips = SchedulerContext.getContext().getIps();
//        int index = seq(lastIIndex, ips.size());
//        Map<Integer, Integer> map = new HashMap<>();
//        IpAddress ipAddress = ips.get(index);
//        int retryCnt=0;
//        try {
//            rpcClient.schedule(ipAddress, jobDesc);
//        } catch (ScheduleTransportException e) {
//            //TODO:加入zk处于异常状态提醒
//            retryCnt++;
//        }
//        lastIIndex = index;
    }

    private int seq(int index, int size) {
        if (index == size - 1) {
            return 0;
        }
        return index + 1;
    }

}

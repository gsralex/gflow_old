package com.gsralex.gflow.scheduler.server;

import com.gsralex.gflow.core.connect.SecurityUtils;
import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.thriftgen.TResult;
import org.apache.log4j.Logger;

public class ScheduleChain {

    private static final Logger LOGGER=Logger.getLogger(ScheduleChain.class);

    private String accessKey;
    public ScheduleChain(String accessKey){
        this.accessKey=accessKey;
    }

    public TResult execute(ScheduleCallback callback, String accessToken) {


        TResult tResult = new TResult();
        if (!SecurityUtils.check(accessKey, accessToken)) {
            tResult.setCode(ErrConstants.ERR_ACCESSTOKEN);
            tResult.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
            return tResult;
        }
        return callback.doSchedule();
    }
}

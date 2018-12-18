package com.gsralex.gflow.scheduler.server;

import com.gsralex.gflow.core.connect.SecurityUtils;
import com.gsralex.gflow.core.constants.ErrConstants;
import com.gsralex.gflow.core.thriftgen.TResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleChain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleChain.class);

    private String accessKey;

    public ScheduleChain(String accessKey) {
        this.accessKey = accessKey;
    }

    public TResp execute(ScheduleCallback callback, String accessToken) {


        TResp tResult = new TResp();
        if (!SecurityUtils.check(accessKey, accessToken)) {
            tResult.setCode(ErrConstants.ERR_ACCESSTOKEN);
            tResult.setMsg(ErrConstants.MSG_ERRACCESTOKEN);
            return tResult;
        }
        return callback.doSchedule();
    }
}

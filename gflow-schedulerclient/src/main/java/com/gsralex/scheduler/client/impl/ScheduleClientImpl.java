package com.gsralex.scheduler.client.impl;

import com.gsralex.gflow.core.connect.SecurityUtils;
import com.gsralex.gflow.core.thriftgen.scheduler.*;
import com.gsralex.gflow.scheduler.enums.JobGroupStatusEnum;
import com.gsralex.scheduler.client.action.scheduler.GetJobGroupResp;
import com.gsralex.scheduler.client.action.scheduler.ScheduleGroupReq;
import com.gsralex.scheduler.client.action.scheduler.ScheduleGroupResp;
import com.gsralex.scheduler.client.ClientCallback;
import com.gsralex.scheduler.client.ClientContext;
import com.gsralex.scheduler.client.ClientWrapper;
import com.gsralex.scheduler.client.ScheduleClient;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gsralex
 * @version 2018/8/19
 */
public class ScheduleClientImpl implements ScheduleClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleClientImpl.class);

    private ClientContext context;
    private ClientWrapper wrapper;

    public ScheduleClientImpl(ClientContext context) {
        this.context = context;
        this.wrapper = new ClientWrapper(context);
    }

    @Override
    public ScheduleGroupResp scheduleGroup(ScheduleGroupReq req) {
        class ClientGroupCallback implements ClientCallback {

            @Override
            public ScheduleGroupResp doSchedule(TScheduleService.Client client) throws TException {
                TGroupJobReq tReq = new TGroupJobReq();
                tReq.setParameter(req.getParameter());
                tReq.setGroupId(req.getFlowGroupId());
                tReq.setAccessToken(getAccessToken());
                TScheduleGroupResp tResp = client.scheduleGroup(tReq);
                ScheduleGroupResp resp = new ScheduleGroupResp();
                resp.setCode(tResp.getCode());
                resp.setMsg(tResp.getMsg());
                resp.setJobGroupId(tResp.getJobGroupId());
                return resp;
            }
        }
        return wrapper.execute(new ClientGroupCallback());
    }

    @Override
    public GetJobGroupResp getJobGroup(long id) {
        class GetJobGroupCallback implements ClientCallback {
            @Override
            public GetJobGroupResp doSchedule(TScheduleService.Client client) throws TException {
                TGetJobGroupReq req = new TGetJobGroupReq();
                req.setAccessToken(getAccessToken());
                req.setId(id);
                TGetJobGroupResp tResp = client.getGroup(req);
                GetJobGroupResp resp=new GetJobGroupResp();
                if(tResp.getJobGroup()!=null) {
                    resp.setJobGroupId(tResp.getJobGroup().getId());
                    resp.setStatus(JobGroupStatusEnum.
                            valueOf(tResp.getJobGroup().getStatus().getValue()));
                }
                return resp;
            }
        }
        return wrapper.execute(new GetJobGroupCallback());
    }

    private String getAccessToken() {
        String accessKey = context.getConfig().getAccessKey();
        return SecurityUtils.encrypt(accessKey);
    }
}

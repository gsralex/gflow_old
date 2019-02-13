package com.gsralex.scheduler.client;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.core.util.DtUtils;
import com.gsralex.gflow.core.util.PropertiesUtils;
import com.gsralex.gflow.scheduler.enums.JobGroupStatusEnum;
import com.gsralex.scheduler.client.action.scheduler.GetJobGroupResp;
import com.gsralex.scheduler.client.action.scheduler.ScheduleGroupReq;
import com.gsralex.scheduler.client.action.scheduler.ScheduleGroupResp;
import com.gsralex.scheduler.client.config.ClientConfig;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/10/3
 */
public class ClientContext {

    private static final String CONFIG_FILEPATH = "/gflow.properties";
    private ClientConfig config;
    private List<IpAddress> schedulerIps = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientContext.class);

    private void init() throws IOException {
        config = PropertiesUtils.getConfig(CONFIG_FILEPATH, ClientConfig.class);
        initIps();
    }

    private void initIps() {
        String[] ips = config.getSchedulerIps().split(",");
        for (String ip : ips) {
            schedulerIps.add(IpAddress.getIp(ip));
        }
    }

    public List<IpAddress> getIps() {
        return schedulerIps;
    }

    public ClientConfig getConfig() {
        return config;
    }

    public static void main(String[] args) throws IOException, ParseException {


        ClientContext context = new ClientContext();
        context.init();
        ScheduleClient scheduleClient = null;// new ScheduleClientImpl(context);

        int id = Integer.parseInt(args[0]);
        String st = args[1];// "20181226";
        String et = args[2];// "20181227";
        String format = "yyyyMMdd";
        Date start = DateUtils.parseDate(st, format);
        Date end = DateUtils.parseDate(et, format);

        int days = DtUtils.diffDays(start, end);
        for (int i = 0; i <= days; i++) {
            Date date = DateUtils.addDays(start, i);
            String bizDate = DateFormatUtils.format(date, format);
            Parameter parameter = new Parameter();
            parameter.put("bizdate", bizDate);
            ScheduleGroupReq req = new ScheduleGroupReq();
            req.setFlowGroupId(id);
            req.setParameter(parameter.toString());
            ScheduleGroupResp resp = scheduleClient.scheduleGroup(req);
            LOGGER.info("schedulegroup: date:" + bizDate + ",groupid:" + req.getFlowGroupId());
            while (true) {
                GetJobGroupResp getResp = scheduleClient.getJobGroup(resp.getJobGroupId());
                if (getResp.getStatus() == JobGroupStatusEnum.FINISH) {
                    break;
                }
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                }
            }
        }

    }
}

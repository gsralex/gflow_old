package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.scheduler.ha.MasterSwitchAction;
import com.gsralex.gflow.scheduler.ha.SlaveSwitchAction;
import com.gsralex.gflow.scheduler.ha.ZkRegisterMaster;
import com.gsralex.gflow.scheduler.parameter.DynamicParam;
import com.gsralex.gflow.scheduler.server.ScheduleTransportException;
import com.gsralex.gflow.scheduler.server.TSchedulerServer;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gsralex
 * @date 2018/2/13
 */
public class SchedulerServer {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerServer.class);

    private SchedulerContext context;

    public SchedulerServer() throws IOException {
        context = new SchedulerContext();
        context.init();
    }

    public void addParameter(DynamicParam parameter) {
        context.addParam(parameter);
    }

    public void serve() throws ScheduleTransportException, IOException {
        LOG.info("====== SchedulerServer STARTING ======");
        TSchedulerServer server = new TSchedulerServer(context);
        server.start();
        LOG.info("====== SchedulerServer STARTED ======");

        //如果有zk，则用zk注册master
        String zkServer = context.getConfig().getZkServer();
        if (zkServer != null && zkServer.length() != 0) {
            ZkRegisterMaster zkRegisterMaster = new ZkRegisterMaster(context);
            if (zkRegisterMaster.registerMaster()) {
                context.setMaster(true);
                context.setMasterIp(context.getMyIp());
            } else {
                zkRegisterMaster.subscribe();
                context.setMaster(false);
                context.setMasterIp(zkRegisterMaster.getMasterIp());
            }
        }
        if (context.isMaster()) {
            //定时任务
            MasterSwitchAction master = new MasterSwitchAction(context);
            master.start();
        } else {
            SlaveSwitchAction slave = new SlaveSwitchAction(context);
            slave.start();
        }
    }

    public static void main(String[] args) throws ScheduleTransportException, IOException {
        SchedulerServer server = new SchedulerServer();
        server.addParameter(getBizdataParam());
        server.serve();
    }

    private static DynamicParam getBizdataParam() {
        return new DynamicParam() {
            @Override
            public String getValue(String key) {
                Pattern pattern = Pattern.compile("bizdate(-(?<day>\\d+)d)?");
                Matcher matcher = pattern.matcher(key);
                matcher.find();
                int day = NumberUtils.toInt(matcher.group("day"), 0);
                return DateFormatUtils.format(DateUtils.addDays(new Date(), day), "yyyyMMdd");
            }
        };
    }
}


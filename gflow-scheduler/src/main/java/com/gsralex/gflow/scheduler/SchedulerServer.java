package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.pub.context.IpAddr;
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
import java.net.InetAddress;
import java.net.UnknownHostException;
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

    public SchedulerServer(boolean master) throws IOException {
        context = SchedulerContext.getInstance();
        context.init();
        context.setMaster(master);
    }

    public void addParameter(DynamicParam parameter) {
        SchedulerContext.getInstance().addParam(parameter);
    }

    public void serve() throws ScheduleTransportException, UnknownHostException {

        LOG.info("====== SchedulerServer STARTING ======");
        TSchedulerServer server = context.getBean(TSchedulerServer.class);
        server.start(context.getConfig().getPort());
        LOG.info("====== SchedulerServer STARTED ======");

        selectMaster(context);
        if (context.isMaster()) {
            MasterSwitchAction master = new MasterSwitchAction();
            master.start();
        } else {
            SlaveSwitchAction slave = new SlaveSwitchAction();
            slave.start();
        }
    }


    private void selectMaster(SchedulerContext context) throws UnknownHostException {
        //如果有zk，则用zk注册master
        if (context.getConfig().getZkActive() != null
                && context.getConfig().getZkActive()) {
            ZkRegisterMaster zkRegisterMaster = new ZkRegisterMaster();
            if (zkRegisterMaster.registerMaster()) {
                context.setMaster(true);
                context.setMasterIp(context.getMyIp());
            } else {
                zkRegisterMaster.subscribe();
                context.setMaster(false);
                context.setMasterIp(zkRegisterMaster.getMasterIp());
            }
        } else {
            IpAddr masterIp = new IpAddr(context.getConfig().getSchedulerMaster());
            InetAddress masterAddr = InetAddress.getByName(masterIp.getIp());
            masterIp.setIp(masterAddr.getHostAddress());
            context.setMasterIp(masterIp);
        }
    }


    public static void main(String[] args) throws ScheduleTransportException, IOException {
        ArgsData argsData = new ArgsData(args);
        // SchedulerServer server = new SchedulerServer(argsData.isMaster());
        SchedulerServer server = new SchedulerServer(true);
        server.addParameter(getBizdataParam());
        server.serve();
    }

    private static final Pattern PATTERN_BIZDATE = Pattern.compile("bizdate(-(?<day>\\d+)d)?");

    private static DynamicParam getBizdataParam() {
        return new DynamicParam() {
            @Override
            public String getValue(String key) {
                Matcher matcher = PATTERN_BIZDATE.matcher(key);
                matcher.find();
                int day = NumberUtils.toInt(matcher.group("day"), 0);
                return DateFormatUtils.format(DateUtils.addDays(new Date(), day), "yyyyMMdd");
            }
        };
    }
}


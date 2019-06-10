package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.rpc.server.RpcServer;
import com.gsralex.gflow.scheduler.client.FlowService;
import com.gsralex.gflow.scheduler.client.ScheduleService;
import com.gsralex.gflow.scheduler.client.TimerService;
import com.gsralex.gflow.scheduler.parameter.DynamicParam;
import com.gsralex.gflow.scheduler.registry.ZkMasterRegistry;
import com.gsralex.gflow.scheduler.registry.ZkSchedulerRegistry;
import com.gsralex.gflow.scheduler.server.FlowServiceImpl;
import com.gsralex.gflow.scheduler.server.ScheduleServiceImpl;
import com.gsralex.gflow.scheduler.server.TimerServiceImpl;
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

    public void serve() throws UnknownHostException, InterruptedException {
        LOG.info("====== SchedulerServer STARTING ======");
        RpcServer rpcServer = new RpcServer();
        rpcServer.registerHandler(FlowService.class, context.getBean(FlowServiceImpl.class));
        rpcServer.registerHandler(TimerService.class, context.getBean(TimerServiceImpl.class));
        rpcServer.registerHandler(ScheduleService.class, context.getBean(ScheduleServiceImpl.class));
        rpcServer.start(context.getConfig().getPort());
        LOG.info("====== SchedulerServer STARTED ======");
        handleNodes(context);
    }

    private void handleNodes(SchedulerContext context) throws UnknownHostException {
        //如果有zk，则用zk注册master
        if (context.getConfig().getZkActive() != null
                && context.getConfig().getZkActive()) {
            ZkMasterRegistry zkMasterRegistry = new ZkMasterRegistry();
            if (zkMasterRegistry.registerMaster()) {
                context.setMaster(true);
                context.setMasterIp(context.getMyIp());
            } else {
                zkMasterRegistry.subscribe();
                context.setMaster(false);
                context.setMasterIp(zkMasterRegistry.getMasterIp());
            }
            ZkSchedulerRegistry zkSchedulerRegistry = new ZkSchedulerRegistry();
            zkSchedulerRegistry.register();
            zkSchedulerRegistry.subscribeScheduler();
            zkSchedulerRegistry.subscribeExecutor();
            //initialize
            context.getSchedulerIpManager().updateServerNodes(zkSchedulerRegistry.listScheduler());
            context.getExecutorClientManager().updateNodes(zkSchedulerRegistry.listExecutor());
        } else {
            IpAddr masterIp = new IpAddr(context.getConfig().getSchedulerMaster());
            InetAddress masterAddr = InetAddress.getByName(masterIp.getIp());
            masterIp.setIp(masterAddr.getHostAddress());
            context.setMasterIp(masterIp);
            //initialize
            ExecutorClientManager executorClientManager = ExecutorClientManager.getInstance();
            executorClientManager.updateNodes(context.getConfig().getExecutorNodes());
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        ArgsTool argsTool = ArgsTool.fromArgs(args);
        argsTool.getBoolean("master");
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


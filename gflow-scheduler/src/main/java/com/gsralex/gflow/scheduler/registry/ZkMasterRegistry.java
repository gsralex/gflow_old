package com.gsralex.gflow.scheduler.registry;

import com.gsralex.gflow.core.constants.ZkConstants;
import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.util.ZkUtils;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.timer.TimerProcess;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.io.IOException;

/**
 * @author gsralex
 * @version 2019/2/15
 */
public class ZkMasterRegistry {

    private SchedulerContext context;
    private ZkClient zkClient;

    public ZkMasterRegistry() {
        context = SchedulerContext.getInstance();
        this.zkClient = new ZkClient(context.getConfig().getZkServer());
    }

    public boolean registerMaster() {
        return ZkUtils.createZNode(ZkConstants.ZKPATH_SCHEDULER_MASTER, context.getMyIp().toString(), CreateMode.EPHEMERAL, zkClient);
    }

    public IpAddr getMasterIp() {
        return new IpAddr(zkClient.readData(ZkConstants.ZKPATH_SCHEDULER_MASTER));
    }

    public void subscribe() {
        zkClient.subscribeDataChanges(ZkConstants.ZKPATH_SCHEDULER_MASTER, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                String masterIp = o.toString();
                if (masterIp.equals(context.getMyIp().toString())) {
                    if (!context.isMaster()) {
                        if (context.getTimerProcess() == null) {
                            TimerProcess process = context.getBean(TimerProcess.class);
                            context.setTimerProcess(process);
                        }
                        context.getTimerProcess().start();
                    }
                    context.setMaster(true);
                    context.setMasterIp(new IpAddr(masterIp));
                } else {
                    if (context.isMaster()) {
                        //停止日期时间
                        context.getTimerProcess().stop();
                    }
                    context.setMaster(false);
                    context.setMasterIp(new IpAddr(masterIp));
                }
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                registerMaster();
            }
        });
    }

    public static void main(String[] args) throws IOException {
        SchedulerContext context = SchedulerContext.getInstance();
        context.init();
        ZkMasterRegistry zkMasterRegistry = new ZkMasterRegistry();
        zkMasterRegistry.registerMaster();
        zkMasterRegistry.subscribe();
        System.in.read();
    }
}

package com.gsralex.gflow.scheduler.ha;

import com.gsralex.gflow.pub.constants.ZkConstants;
import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.scheduler.SchedulerContext;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.io.IOException;

/**
 * @author gsralex
 * @version 2019/2/15
 */
public class ZkRegisterMaster {

    private SchedulerContext context;
    private ZkClient zkClient;

    public ZkRegisterMaster(SchedulerContext context) {
        this.context = context;
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
                        //slave -> master
                        SlaveSwitchAction slave = new SlaveSwitchAction(context);
                        slave.stop();
                        MasterSwitchAction master = new MasterSwitchAction(context);
                        master.start();
                    }
                    context.setMaster(true);
                    context.setMasterIp(new IpAddr(masterIp));
                } else {
                    if (context.isMaster()) {
                        //master->slave
                        MasterSwitchAction master = new MasterSwitchAction(context);
                        master.stop();
                        SlaveSwitchAction slave = new SlaveSwitchAction(context);
                        slave.start();
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
        SchedulerContext context = new SchedulerContext();
        context.init();
        ZkRegisterMaster zkRegisterMaster = new ZkRegisterMaster(context);
        zkRegisterMaster.registerMaster();
        zkRegisterMaster.subscribe();
        System.in.read();
    }
}

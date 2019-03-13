package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.pub.context.IpAddr;
import com.gsralex.gflow.pub.context.IpManager;
import com.gsralex.gflow.pub.util.SecurityUtils;
import com.gsralex.gflow.scheduler.configuration.SchedulerConfig;
import com.gsralex.gflow.scheduler.configuration.SpringConfiguration;
import com.gsralex.gflow.scheduler.flow.FlowGuideMap;
import com.gsralex.gflow.scheduler.parameter.DynamicParam;
import com.gsralex.gflow.scheduler.parameter.DynamicParamContext;
import com.gsralex.gflow.scheduler.timer.TimerProcess;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gsralex
 * @version 2018/9/21
 */
public class SchedulerContext {

    private static final SchedulerContext CURRENT = new SchedulerContext();

    private SchedulerContext() {
    }

    public static SchedulerContext getInstance() {
        return CURRENT;
    }

    private static final String CONFIG_FILEPATH = "/gflow.properties";
    private SchedulerConfig config;
    /**
     * 所有未完成的flow
     */
    private FlowGuideMap flowGuideMap = new FlowGuideMap();
    /**
     * 动态参数处理
     */
    private DynamicParamContext paramContext = DynamicParamContext.getContext();


    //ha
    /**
     * 服务当前状态
     */
    private boolean master = false;

    private IpAddr masterIp;
    /**
     * 当前主机名端口
     */
    private IpAddr myIp;


    private String accessToken;

    private HbContext hbContext;

    private TimerProcess timerProcess;

    /**
     * spring
     */
    private static ApplicationContext context;

    private Map<String, IpManager> executorIpManager = new ConcurrentHashMap<>();

    private IpManager schedulerIpManager;

    public void init() throws IOException {
        this.config = SchedulerConfig.load();

        //主ip
        masterIp = new IpAddr(config.getSchedulerMaster());

        InetAddress addr = InetAddress.getLocalHost();
        myIp = new IpAddr(addr.getHostAddress(), config.getPort());

        schedulerIpManager = new IpManager(config.getSchedulerIps());

        for (Map.Entry<String, List<IpAddr>> ipEntry : config.getExecutorIpMap().entrySet()) {
            IpManager ipManager = new IpManager(ipEntry.getValue());
            executorIpManager.put(ipEntry.getKey(), ipManager);
        }

        initSpring();

    }


    private void initSpring() {
        context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    }

    public <T> T getBean(Class<T> type, Object... objects) {
        return context.getBean(type, objects);
    }


    public FlowGuideMap getFlowGuideMap() {
        return flowGuideMap;
    }

    public SchedulerConfig getConfig() {
        return config;
    }

    /**
     * 添加参数
     *
     * @param param
     */
    public void addParam(DynamicParam param) {
        this.paramContext.addParam(param);
    }

    public boolean isMaster() {
        return master;

    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public IpAddr getMyIp() {
        return myIp;
    }

    public String getAccessToken() {
        return SecurityUtils.encrypt(this.getConfig().getAccessKey());
    }


    public HbContext getHbContext() {
        return hbContext;
    }

    public void setHbContext(HbContext hbContext) {
        this.hbContext = hbContext;
    }

    public TimerProcess getTimerProcess() {
        return timerProcess;
    }

    public void setTimerProcess(TimerProcess timerProcess) {
        this.timerProcess = timerProcess;
    }

    public IpAddr getMasterIp() {
        return masterIp;
    }

    public void setMasterIp(IpAddr masterIp) {
        this.masterIp = masterIp;
    }


    public void setTagExecutorIp(String tag, List<IpAddr> ipList) {
        if (executorIpManager.containsKey(tag)) {
            executorIpManager.put(tag, new IpManager(ipList));
        } else {
            executorIpManager.get(tag).setIp(ipList);
        }
    }

    public IpManager getExecutorIpManager(String tag) {
        return executorIpManager.get(tag);
    }

    public Map<String, IpManager> getExecutorIpManager() {
        return executorIpManager;

    }

    public IpManager getSchedulerIpManager() {
        return schedulerIpManager;
    }


    public static void main(String[] args) throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        System.out.println(addr);
    }
}

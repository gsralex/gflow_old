package com.gsralex.gflow.scheduler;

import com.gsralex.gdata.bean.jdbc.JdbcUtils;
import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.scheduler.flow.FlowGuideMap;
import com.gsralex.gflow.core.zk.ExecutorIpData;
import com.gsralex.gflow.scheduler.parameter.DynamicParam;
import com.gsralex.gflow.scheduler.parameter.DynamicParamContext;
import com.gsralex.gflow.scheduler.retry.RetryProcessor;
import com.gsralex.gflow.scheduler.time.TimerTaskProcessor;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gsralex
 * @version 2018/9/21
 */
public class SchedulerContext {
    private static SchedulerContext currentContext = new SchedulerContext();

    private GFlowContext gFlowContext;

    private ExecutorIpData ipData;

    /**
     * 所有未完成的flow
     */
    private FlowGuideMap flowGuideMap = new FlowGuideMap();
    /**
     * 动态参数处理
     */
    private DynamicParamContext paramContext = DynamicParamContext.getContext();

    private JdbcUtils jdbcUtils;


    private SchedulerContext() {

    }

    public void setGflowContext(GFlowContext context) {
        gFlowContext = context;
        this.ipData = new ExecutorIpData(context);
        initializeJdbc();

    }

    private void initializeJdbc() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(gFlowContext.getConfig().getDbUrl());
        dataSource.setUsername(gFlowContext.getConfig().getDbUsername());
        dataSource.setPassword(gFlowContext.getConfig().getDbPassword());
        dataSource.setDriverClassName(gFlowContext.getConfig().getDbDriver());
        jdbcUtils = new JdbcUtils(dataSource);
    }


    public static SchedulerContext getContext() {
        return currentContext;
    }

    public GFlowContext getGFlowContext() {
        return gFlowContext;
    }

    public List<IpAddress> getIps() {
        return ipData.getIps();
    }

    public FlowGuideMap getFlowGuideMap() {
        return flowGuideMap;
    }

    /**
     * 添加参数
     *
     * @param param
     */
    public void addParam(DynamicParam param) {
        this.paramContext.addParam(param);
    }

    public JdbcUtils getJdbcUtils() {
        return jdbcUtils;
    }
}

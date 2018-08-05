package com.gsralex.gflow.scheduler.service.impl;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.util.DtUtils;
import com.gsralex.gflow.scheduler.dao.ExecuteDao;
import com.gsralex.gflow.scheduler.dao.FlowJobDao;
import com.gsralex.gflow.scheduler.dao.impl.ExecuteDaoImpl;
import com.gsralex.gflow.scheduler.dao.impl.FlowJobDaoImpl;
import com.gsralex.gflow.scheduler.domain.flow.ExecuteTimeEnum;
import com.gsralex.gflow.scheduler.domain.flow.GFlowExecuteConfig;
import com.gsralex.gflow.scheduler.service.ExecuteService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ExecuteServiceImpl implements ExecuteService {

    private ExecuteDao executeDao;
    private FlowJobDao flowJobDao;

    public ExecuteServiceImpl(GFlowContext context) {
        executeDao = new ExecuteDaoImpl(context.getJdbcContext());
        flowJobDao = new FlowJobDaoImpl(context);
    }

    public List<GFlowExecuteConfig> getNeedExecuteConfig() {
        List<GFlowExecuteConfig> list = new ArrayList<>();
        List<GFlowExecuteConfig> configList = executeDao.listExecuteConfig();
        for (GFlowExecuteConfig config : configList) {
            if (config.getActive()) {
                ExecuteTimeEnum timeEnum = ExecuteTimeEnum.valueOf(config.getTimeType());
                switch (timeEnum) {
                    case SetTime: {
                        Date executeTime = DtUtils.getTodayTime(config.getTime());
                        Date now = new Date();
                        int date = DtUtils.getBizDate();
                        if (executeTime.after(now)) {//不可以执行
                            break;
                        }
                        Date createTime = DtUtils.parseUnixTime(config.getCreateTime());
                        if (createTime.after(executeTime)) {//创建时间晚于设定的执行时间
                            break;
                        }
                        //验证是否在今天执行过
                        int cnt = flowJobDao.getJobGroupByExecute(config.getTriggerGroupId(), date);
                        if (cnt == 0) {
                            list.add(config);
                        }
                    }
                }
            }
        }
        return list;
    }

}

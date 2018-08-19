package com.gsralex.gflow.scheduler.time;

import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.enums.ExecuteTimeEnum;
import com.gsralex.gflow.core.domain.GFlowExecuteConfig;
import com.gsralex.gflow.core.util.DtUtils;
import com.gsralex.gflow.scheduler.TimeService;
import com.gsralex.gflow.scheduler.sql.ExecuteDao;
import com.gsralex.gflow.scheduler.sql.FlowJobDao;
import com.gsralex.gflow.scheduler.sql.impl.ExecuteDaoImpl;
import com.gsralex.gflow.scheduler.sql.impl.FlowJobDaoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TimeServiceImpl implements TimeService {

    private ExecuteDao executeDao;
    private FlowJobDao flowJobDao;

    public TimeServiceImpl(GFlowContext context) {
        executeDao = new ExecuteDaoImpl(context.getJdbcContext());
        flowJobDao = new FlowJobDaoImpl(context);
    }

    public List<GFlowExecuteConfig> listNeedActionGroup() {
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
                        int cnt = flowJobDao.getJobGroupByExecute(config.getId(), date);
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

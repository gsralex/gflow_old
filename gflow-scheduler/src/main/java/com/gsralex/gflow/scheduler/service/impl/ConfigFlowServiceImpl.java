package com.gsralex.gflow.scheduler.service.impl;

import com.gsralex.gflow.scheduler.dao.ConfigDao;
import com.gsralex.gflow.scheduler.dao.FlowJobDao;
import com.gsralex.gflow.scheduler.domain.flow.*;
import com.gsralex.gflow.scheduler.service.ConfigFlowService;
import com.gsralex.gflow.scheduler.service.FlowService;
import com.gsralex.gflow.scheduler.util.DtUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public class ConfigFlowServiceImpl implements ConfigFlowService {

    private ConfigDao configDao;

    private FlowJobDao flowJobDao;


    private FlowService flowService;

    @Override
    public void executeScheduleConfig() {
        List<GFlowExecuteConfig> configList = configDao.getExecuteActiveList();
        for (GFlowExecuteConfig config : configList) {
            ExecuteTimeEnum timeType = ExecuteTimeEnum.valueOf(config.getTimeType());
            long triggerGroupId = config.getTriggerGroupId();
            switch (timeType) {
                case SetTime: {
                    //HH:mm:ss
                    if (!StringUtils.isEmpty(config.getTime())) {
                        Date executeTime = DtUtils.getTodayTime(config.getTime());
                        Date now = new Date();
                        if (executeTime.after(now)) {
                            String date = DtUtils.formatBizDate(now);
                            if (!this.getIsExecute(config.getTriggerGroupId(), date)) {
                                //flowService.startGroup(triggerGroupId, "");
                            }
                        }
                    }
                    break;
                }
                case Interval: {
                    break;
                }
            }
        }
    }

    private Boolean getIsExecute(long triggerGroupId, String date) {
//        Boolean executed = dateCache.getExecute(triggerGroupId, date);
//        if (executed == null) {
//            GFlowJobGroup jobGroup = flowJobDao.getJobGroupByExecute(triggerGroupId, date);
//            executed = jobGroup != null ? true : false;
//            dateCache.markExecute(triggerGroupId, date);
//        }
//        return executed;
        return false;
    }


    public static void main(String[] args) {
        DtUtils.getTodayTime("06:00:00");
    }


}

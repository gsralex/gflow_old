package com.gsralex.gflow.scheduler.impl;

import com.gsralex.gflow.core.dao.ExecutionDao;
import com.gsralex.gflow.core.dao.TriggerDao;
import com.gsralex.gflow.core.domain.GFlowExecution;
import com.gsralex.gflow.core.domain.GFlowJobGroup;
import com.gsralex.gflow.core.enums.ExecutionEnum;
import com.gsralex.gflow.core.util.DatetimeUtils;
import com.gsralex.gflow.scheduler.TimingService;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.gsralex.gflow.core.enums.ExecutionEnum.valueOf;

/**
 * @author gsralex
 * @date 2018/2/18
 */
public class TimingServiceImpl implements TimingService {


    private ExecutionDao executionDao;
    private TriggerDao triggerDao;
    private static Logger LOGGER = Logger.getLogger(TimingService.class);


    @Override
    public void start() {
        while (true) {
            List<GFlowExecution> executionList = listNeedSchedule();
            for (GFlowExecution execution : executionList) {
                
            }
            try {
                Thread.sleep(1000); //每一秒请求一次调度
            } catch (Throwable e) {
                LOGGER.error("com.gsralex.gflow.scheduler.TimingService.start", e);
            }
        }
    }


    private List<GFlowExecution> listNeedSchedule() {
        List<GFlowExecution> executionList = executionDao.listActiveExecution();
        Date now = new Date();
        List<GFlowExecution> needExecutionList = new ArrayList<>();
        for (GFlowExecution execution : executionList) {
            ExecutionEnum type = valueOf(execution.getType());
            String time = execution.getTime();
            switch (type) {
                case TIMINGDAY: { //每天执行
                    if (DatetimeUtils.after(now, time)) {
                        needExecutionList.add(execution);
                    }
                    break;
                }
                case TIMINGWEEK: { //每周执行
                    if (DatetimeUtils.dayEqualWeek(now, execution.getWeek())) {
                        if (DatetimeUtils.after(now, time)) {
                            needExecutionList.add(execution);
                        }
                    }
                    break;
                }
                case TIMINGMONTH: { //每月执行
                    if (DatetimeUtils.dayEqualMonth(now, execution.getMonth())) {
                        if (DatetimeUtils.after(now, time)) {
                            needExecutionList.add(execution);
                        }
                    }
                    break;
                }
            }
        }
        return checkDataNeedSchedule(executionList);
    }

    private List<GFlowExecution> checkDataNeedSchedule(List<GFlowExecution> list) {
        List<GFlowExecution> r = new ArrayList<>();
        List<Long> groupIdList = list.stream().map(x -> x.getTriggerGroupId()).collect(Collectors.toList());
        int ds = DatetimeUtils.getDs();
        List<GFlowJobGroup> executedList = triggerDao.listJobGroup(groupIdList, ds);

        Set<Long> executedIdSet = jobGroupSet(executedList);
        for (GFlowExecution item : list) {
            if (!executedIdSet.contains(item.getTriggerGroupId())) {
                r.add(item);
            }
        }
        return r;
    }

    private Set<Long> jobGroupSet(List<GFlowJobGroup> list) {
        Set<Long> idSet = new HashSet<>();
        for (GFlowJobGroup item : list) {
            idSet.add(item.getTriggerGroupId());
        }
        return idSet;
    }
}

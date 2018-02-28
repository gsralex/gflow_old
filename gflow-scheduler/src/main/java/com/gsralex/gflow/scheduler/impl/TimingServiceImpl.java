package com.gsralex.gflow.scheduler.impl;

import com.gsralex.gflow.core.dao.ExecutionDao;
import com.gsralex.gflow.core.domain.GFlowExecution;
import com.gsralex.gflow.core.enums.ExecutionEnum;
import com.gsralex.gflow.scheduler.TimingService;
import org.apache.log4j.Logger;

import java.util.List;

import static com.gsralex.gflow.core.enums.ExecutionEnum.valueOf;

/**
 * @author gsralex
 * @date 2018/2/18
 */
public class TimingServiceImpl implements TimingService {


    private ExecutionDao dao;
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
        List<GFlowExecution> executionList = dao.listActiveExecution();
        for (GFlowExecution execution : executionList) {
            ExecutionEnum type = valueOf(execution.getType());
            switch (type) {
                case TIMINGDAY: { //定时器
                    //hh:mm:ss
                    String time = execution.getTime();
                    break;
                }
                case TIMINGWEEK: {
                    break;
                }
                case TIMINGMONTH: {
                    break;
                }
            }
        }
        return executionList;
    }
}

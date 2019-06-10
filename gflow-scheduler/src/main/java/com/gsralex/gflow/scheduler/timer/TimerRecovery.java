package com.gsralex.gflow.scheduler.timer;

import com.gsralex.gflow.core.domain.TimerConfigPo;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.dao.JobDao;
import com.gsralex.gflow.scheduler.dao.TimerExecuteRecord;
import com.gsralex.gflow.scheduler.service.BizJobService;
import com.gsralex.gflow.scheduler.service.BizTimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时任务恢复
 *
 * @author gsralex
 * @version 2018/12/18
 */
@Component
public class TimerRecovery {

    @Autowired
    private BizJobService jobService;
    @Autowired
    private BizTimerService timerService;

    private SchedulerContext context = SchedulerContext.getInstance();

    public void recover() {
        List<TimerConfigPo> timerList = timerService.listTimer();
        List<Long> timerIdList = timerList.stream().map(x -> x.getId()).collect(Collectors.toList());
        List<TimerExecuteRecord> execList = jobService.listJobGroupByTimer(timerIdList);
        Map<Long, Long> execMap = new HashMap<>();
        for (TimerExecuteRecord exec : execList) {
            execMap.put(exec.getTimerConfigId(), exec.getCreateTime());
        }
        for (TimerConfigPo timer : timerList) {
            Long execTime = execMap.get(timer.getId());
            if (execTime == null) {
                execTime = 0L;
            }
            TimerTask timerTask = new TimerTask(timer, execTime);
            context.getTimerProcess().setTimer(timerTask);
        }
    }
}

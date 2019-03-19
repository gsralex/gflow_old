package com.gsralex.gflow.web.dao;

import com.gsralex.gflow.core.domain.TimerConfigPo;
import com.gsralex.gflow.web.model.TimerVo;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface TimerDao {

    TimerConfigPo getTimer(long id);

    List<TimerVo> listTimer(int pageSize, int pageIndex);

    int countTimer();
}

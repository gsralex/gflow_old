package com.gsralex.gflow.web.dao;

import com.gsralex.gflow.pub.domain.TimerConfig;
import com.gsralex.gflow.web.model.TimerVo;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/9
 */
public interface TimerDao {

    TimerConfig getTimer(long id);

    List<TimerVo> listTimer(int pageSize, int pageIndex);

    int countTimer();
}

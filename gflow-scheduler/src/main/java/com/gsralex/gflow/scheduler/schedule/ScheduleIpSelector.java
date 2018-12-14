package com.gsralex.gflow.scheduler.schedule;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.domain.ActionTag;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.sql.ConfigDao;
import com.gsralex.gflow.scheduler.sql.impl.ConfigDaoImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gsralex
 * @version 2018/12/6
 */
public class ScheduleIpSelector {

    private ConfigDao configDao;

    public ScheduleIpSelector(SchedulerContext context) {
        configDao = new ConfigDaoImpl(context.getJdbcUtils());
    }

    public IpAddress getIpAddress(long tagId) {
        List<ActionTag> tagList = configDao.listActionTag();
        for (ActionTag tag : tagList) {
            if (tag.getId() == tagId) {
                List<IpAddress> ips = new ArrayList<>();
                String[] servers = StringUtils.split(tag.getServers(), ",");
                for (String server : servers) {
                    String[] ipport = StringUtils.split(server, ":");
                    ips.add(new IpAddress(ipport[0], Integer.parseInt(ipport[1])));
                }
                return ips.get(0);
            }
        }
        return null;
    }

}

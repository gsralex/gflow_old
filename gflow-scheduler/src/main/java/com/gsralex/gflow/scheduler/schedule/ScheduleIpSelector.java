package com.gsralex.gflow.scheduler.schedule;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.scheduler.SchedulerContext;
import com.gsralex.gflow.scheduler.domain.ActionTag;
import com.gsralex.gflow.scheduler.dao.ActionDao;
import com.gsralex.gflow.scheduler.dao.impl.ActionDaoImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2018/12/6
 */
public class ScheduleIpSelector {

    private ActionDao actionDao;

    public ScheduleIpSelector(SchedulerContext context) {
        actionDao = new ActionDaoImpl(context.getJdbcUtils());
    }

    public IpAddress getIpAddress(long tagId) {
        List<ActionTag> tagList = actionDao.listActionTag();
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

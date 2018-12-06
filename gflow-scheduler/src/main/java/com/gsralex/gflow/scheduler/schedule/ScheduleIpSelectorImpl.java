package com.gsralex.gflow.scheduler.schedule;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.domain.ActionTag;
import com.gsralex.gflow.scheduler.ScheduleIpSelector;
import com.gsralex.gflow.scheduler.sql.ConfigDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gsralex
 * @version 2018/12/6
 */
@Service
public class ScheduleIpSelectorImpl implements ScheduleIpSelector {

    @Autowired
    private ConfigDao configDao;

    private Map<Long, List<IpAddress>> ipMap = new HashMap<>();

    public ScheduleIpSelectorImpl() {
        List<ActionTag> tagList = configDao.listActionTag();
        for (ActionTag tag : tagList) {
            List<IpAddress> ips = new ArrayList<>();
            String[] servers = StringUtils.split(tag.getServers(), ",");
            for (String server : servers) {
                String[] ipport = StringUtils.split(server, ":");
                ips.add(new IpAddress(ipport[0], Integer.parseInt(ipport[1])));
            }
            ipMap.put(tag.getId(), ips);
        }
    }

    public IpAddress getIpAddress(long tagId) {
        return ipMap.get(tagId).get(0);
    }

}

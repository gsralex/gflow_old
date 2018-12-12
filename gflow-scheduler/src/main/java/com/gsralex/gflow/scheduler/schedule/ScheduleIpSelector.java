package com.gsralex.gflow.scheduler.schedule;

import com.gsralex.gflow.core.context.IpAddress;
import com.gsralex.gflow.core.domain.ActionTag;
import com.gsralex.gflow.scheduler.sql.ConfigDao;
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
@Service
public class ScheduleIpSelector {

    @Autowired
    private ConfigDao configDao;

//    private static Map<Long, IpAddress[]> ipMaps = new HashMap<>();

//    public void init() {
//
//        List<ActionTag> tagList = configDao.listActionTag();
//        for (ActionTag tag : tagList) {
//            List<Ip>
//            ipMaps.put(tag.getId(),)
//        }
//
//    }

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

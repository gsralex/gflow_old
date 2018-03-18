package com.gsralex.gflow.core.dao;

import com.gsralex.gdata.jdbc.JdbcUtils;
import com.gsralex.gflow.core.context.GFlowContext;
import com.gsralex.gflow.core.model.scheduler.ActionDeploy;

import java.util.List;

/**
 * @author gsralex
 * @version 2018/3/17
 */
public class ActionDao {


    private JdbcUtils jdbcUtils;

    public ActionDao(GFlowContext context) {
        jdbcUtils = context.getJdbcUtils();
    }

    public List<ActionDeploy> getActionDeployList(long actionId) {
        String sql = "select ip,port,class_name,name from gflow_actiondeploy as ad left join gflow_deploy as d " +
                "on ad.deploy_id=d.id left join gflow_action as a on ad.action_id=a.id where ad.action_id=?";

        return jdbcUtils.query(sql, new Object[]{actionId}, ActionDeploy.class);
    }

}

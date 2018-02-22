package com.gsralex.gflow.core.dao.helper;

import com.gsralex.gflow.core.GFlowContext;
import com.gsralex.gflow.core.config.GFlowConfig;
import com.gsralex.gflow.core.domain.GFlowAction;
import com.gsralex.gflow.core.domain.GFlowJobGroup;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author gsralex
 * @date 2018/2/22
 */
public class JdbcUtilsTest {

    private JdbcUtils jdbcUtils;

    @Before
    public void setUpBeforeClass() throws Exception {
        GFlowContext context = new GFlowContext();
        context.init();
        jdbcUtils = new JdbcUtils(context.getDataSource());
    }


    @Test
    public void save() throws Exception {
        GFlowJobGroup jobGroup=new GFlowJobGroup();
        jobGroup.setTriggerGroupId(1);
        jdbcUtils.save(jobGroup);
    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void executeUpdate() throws Exception {
        jdbcUtils.executeUpdate("delete from gflow_config", null);
    }

    @Test
    public void executeQuery() throws Exception {

    }

}
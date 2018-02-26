package com.gsralex.gflow.core.dao.helper;

import com.gsralex.gflow.core.GFlowContext;
import com.gsralex.gflow.core.config.GFlowConfig;
import com.gsralex.gflow.core.domain.GFlowAction;
import com.gsralex.gflow.core.domain.GFlowJobGroup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        GFlowJobGroup jobGroup = new GFlowJobGroup();
        jobGroup.setTriggerGroupId(1);
        boolean r = jdbcUtils.save(jobGroup);
        Assert.assertEquals(r, true);
    }

    @Test
    public void batchSave() throws SQLException {
        long start = new Date().getTime();
        List<GFlowJobGroup> jobGroupList = new ArrayList<>();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            GFlowJobGroup jobGroup = new GFlowJobGroup();
            jobGroup.setTriggerGroupId(1);
            jobGroupList.add(jobGroup);
        }
        int r = jdbcUtils.batchSave(jobGroupList);
        Assert.assertEquals(r, size);
        long end = new Date().getTime();
        System.out.println("time:" + (end - start));
    }

    @Test
    public void update() throws Exception {
        GFlowJobGroup jobGroup = new GFlowJobGroup();
        jobGroup.setId(1);
        jobGroup.setTriggerGroupId(1234);
        jobGroup.setCreateTime(1235);
        jobGroup.setStartTime(12345);
        jobGroup.setEndTime(123456);
        jobGroup.setStartTriggerId(123);
        boolean r = jdbcUtils.update(jobGroup);
        Assert.assertEquals(r, true);
    }

    @Test
    public void batchUpdate() throws SQLException {
        List<GFlowJobGroup> jobGroupList = new ArrayList<>();
        long start = new Date().getTime();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            GFlowJobGroup jobGroup = new GFlowJobGroup();
            jobGroup.setId(i + 1);
            jobGroup.setTriggerGroupId(1234);
            jobGroup.setCreateTime(1235);
            jobGroup.setStartTime(12345);
            jobGroup.setEndTime(123456);
            jobGroup.setStartTriggerId(123);
            jobGroupList.add(jobGroup);
        }
        int r = jdbcUtils.batchUpdate(jobGroupList);
        Assert.assertEquals(r, size);
        long end = new Date().getTime();
        System.out.println("time:" + (end - start));
    }

    @Test
    public void executeUpdate() throws Exception {
        jdbcUtils.executeUpdate("delete from gflow_config", null);
    }

    @Test
    public void executeQuery() throws Exception {

    }

}
package com.gsralex.gflow.core.config;


import com.gsralex.gflow.core.util.PropertyName;

import java.util.Date;

/**
 * @author gsralex
 * @date 2018/2/18
 */
public class GFlowConfig {

    @PropertyName(name = "gflow.db.driver")
    public String dbDriver;

    @PropertyName(name = "gflow.db.url")
    public String dbUrl;

    @PropertyName(name = "gflow.db.username")
    public String dbUserName;

    @PropertyName(name = "gflow.db.password")
    public String dbUserPass;


    @PropertyName(name = "gflow.db.dbPrefix1")
    public int dbPrefix1;

    @PropertyName(name = "gflow.db.dbPrefix2")
    public long dbPrefix2;

    @PropertyName(name = "gflow.db.dbPrefix3")
    public double dbPrefix3;

    @PropertyName(name = "gflow.db.dbPrefix4")
    public Date dbPrefix4;

}

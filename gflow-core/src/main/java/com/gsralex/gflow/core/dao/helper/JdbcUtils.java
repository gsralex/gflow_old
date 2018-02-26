package com.gsralex.gflow.core.dao.helper;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gsralex
 * @date 2018/2/22
 */
public class JdbcUtils {

    private static Logger LOGGER = Logger.getLogger(JdbcUtils.class);

    private DataSource dataSource;


    JdbcUtils(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> boolean save(T t) throws SQLException {
        if (t == null)
            return false;
        Object[] objArray = getInsertObjects(t);
        String sql = getInsertSql(t.getClass());
        LOGGER.debug("sql:" + sql);
        return executeUpdate(sql, objArray) != 0 ? true : false;
    }

    public <T> int batchSave(List<T> list) throws SQLException {
        if (list == null || list.size() == 0)
            return 0;
        Class<T> type = (Class<T>) list.get(0).getClass();
        String sql = getInsertSql(type);
        List<Object[]> objectList = new ArrayList<>();
        for (T t : list) {
            objectList.add(getInsertObjects(t));
        }
        return executeBatch(sql, objectList);
    }

    private <T> Object[] getInsertObjects(T t) {
        FieldMapper mapper = ModelMapper.getMapper(t.getClass());
        FieldValue fieldValue = new FieldValue(t, t.getClass());
        List<Object> objects = new ArrayList<>();
        for (Map.Entry<String, FieldColumn> entry : mapper.getMapper().entrySet()) {
            FieldColumn column = entry.getValue();
            if (!column.isId()) {
                Object value = fieldValue.getValue(entry.getValue().getClass(), entry.getKey());
                objects.add(value);
            }
        }
        Object[] objArray = new Object[objects.size()];
        objects.toArray(objArray);
        return objArray;
    }

    private <T> String getInsertSql(Class<T> type) {
        FieldMapper mapper = ModelMapper.getMapper(type);
        String sql = String.format("insert into `%s`", mapper.getTableName());
        String insertSql = "(";
        String valueSql = " values(";
        for (Map.Entry<String, FieldColumn> entry : mapper.getMapper().entrySet()) {
            FieldColumn column = entry.getValue();
            if (!column.isId()) {
                insertSql += String.format("`%s`,", column.getAliasName());
                valueSql += "?,";
            }
        }
        insertSql = StringUtils.removeEnd(insertSql, ",");
        insertSql += ")";
        valueSql = StringUtils.removeEnd(valueSql, ",");
        valueSql += ")";
        return sql + insertSql + valueSql;
    }


    public <T> boolean update(T t) throws SQLException {
        if (t == null)
            return false;
        String sql = getUpdateSql(t.getClass());
        Object[] objects = getUpdateObjects(t);
        return executeUpdate(sql, objects) != 0 ? true : false;
    }

    public <T> int batchUpdate(List<T> list) throws SQLException {
        if (list == null || list.size() == 0) {
            return 0;
        }
        Class<T> type = (Class<T>) list.get(0).getClass();
        String sql = getUpdateSql(type);
        List<Object[]> objectList = new ArrayList<>();
        for (T t : list) {
            objectList.add(getUpdateObjects(t));
        }
        return executeBatch(sql, objectList);
    }

    private <T> String getUpdateSql(Class<T> type) {
        FieldMapper mapper = ModelMapper.getMapper(type);
        String sql = String.format("update `%s` set ", mapper.getTableName());
        for (Map.Entry<String, FieldColumn> entry : mapper.getMapper().entrySet()) {
            FieldColumn column = entry.getValue();
            if (!column.isId()) {
                sql += String.format("`%s`=?,", column.getAliasName());
            }
        }
        sql = StringUtils.removeEnd(sql, ",");
        sql += " where 1=1 ";
        for (Map.Entry<String, FieldColumn> entry : mapper.getMapper().entrySet()) {
            FieldColumn column = entry.getValue();
            if (column.isId()) {
                sql += String.format("and %s=?,", column.getAliasName());
            }
        }
        sql=StringUtils.removeEnd(sql,",");
        return sql;
    }

    private <T> Object[] getUpdateObjects(T t) {
        FieldMapper mapper = ModelMapper.getMapper(t.getClass());
        FieldValue fieldValue = new FieldValue(t, t.getClass());
        List<Object> objects = new ArrayList<>();

        for (Map.Entry<String, FieldColumn> entry : mapper.getMapper().entrySet()) {
            FieldColumn column = entry.getValue();
            if (!column.isId()) {
                Object value = fieldValue.getValue(entry.getValue().getClass(), entry.getKey());
                objects.add(value);
            }
        }
        for (Map.Entry<String, FieldColumn> entry : mapper.getMapper().entrySet()) {
            FieldColumn column = entry.getValue();
            if (column.isId()) {
                Object value = fieldValue.getValue(entry.getValue().getClass(), entry.getKey());
                objects.add(value);
            }
        }
        Object[] objArray = new Object[objects.size()];
        objects.toArray(objArray);
        return objArray;
    }


    public int executeUpdate(String sql, Object[] objects) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = pre(sql, objects);
            return ps.executeUpdate();
        } finally {
            close(ps);
        }
    }

    public <T> List<T> query(String sql, Object[] objects, Class<T> type) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = pre(sql, objects);
            rs = ps.executeQuery();
            return null;
        } finally {
            closeRs(rs);
            close(ps);
        }
    }

    public int executeBatch(String sql, List<Object[]> objectList) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = preBatch(sql, objectList);
            int[] r = ps.executeBatch();
            ps.getConnection().commit();
            return getBatchResult(r);
        } finally {
            close(ps);
        }
    }

    private int getBatchResult(int[] r) {
        int cnt = 0;
        for (int item : r) {
            if (item != Statement.EXECUTE_FAILED) {
                cnt++;
            }
        }
        return cnt;
    }

    private <T> List<T> mapperList(ResultSet rs, Class<T> type) throws SQLException, IllegalAccessException, InstantiationException {
        List<T> list = new ArrayList<>();
        FieldMapper fieldMapper = ModelMapper.getMapper(type);
        while (rs.next()) {
            T instance = type.newInstance();
            FieldValue fieldValue = new FieldValue(instance, type);
            list.add(mapper(rs, fieldMapper, fieldValue));
        }
        return list;
    }

    private <T> T mapperEntity(ResultSet rs, Class<T> type) throws SQLException, IllegalAccessException, InstantiationException {
        T instance = type.newInstance();
        FieldMapper fieldMapper = ModelMapper.getMapper(type);
        FieldValue fieldValue = new FieldValue(instance, type);
        if (rs.next()) {
            return mapper(rs, fieldMapper, fieldValue);
        }
        return null;
    }

    private <T> T mapper(ResultSet rs, FieldMapper fieldMapper, FieldValue fieldValue) throws SQLException {
        try {
            for (Map.Entry<String, FieldColumn> item : fieldMapper.getMapper().entrySet()) {
                String name = item.getKey();
                String columnName = item.getValue().getAliasName();
                switch (item.getValue().getTypeName()) {
                    case "java.lang.String": {
                        fieldValue.setValue(String.class, name, rs.getString(columnName));
                        break;
                    }
                    case "int": {
                        fieldValue.setValue(Integer.class, name, rs.getInt(name));
                        break;
                    }
                    case "long": {
                        fieldValue.setValue(Long.class, name, rs.getLong(name));
                        break;
                    }
                    case "double": {
                        fieldValue.setValue(Double.class, name, rs.getDouble(name));
                        break;
                    }
                    case "float": {
                        fieldValue.setValue(Float.class, name, rs.getFloat(name));
                        break;
                    }
                    case "boolean": {
                        fieldValue.setValue(Boolean.class, name, rs.getBoolean(name));
                        break;
                    }
                }
            }
            return (T) fieldValue.getInstance();
        } catch (Throwable e) {
            return null;
        }
    }


    private PreparedStatement pre(String sql, Object[] objects) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        if (objects != null && objects.length != 0) {
            for (int i = 0; i < objects.length; i++) {
                ps.setObject(i + 1, objects[i]);
            }
        }
        return ps;
    }

    private PreparedStatement preBatch(String sql, List<Object[]> objectsArray) throws SQLException {
        Connection conn = getConnection();
        conn.setAutoCommit(false);
        PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        for (Object[] objects : objectsArray) {
            for (int i = 0, size = objects.length; i < size; i++) {
                ps.setObject(i + 1, objects[i]);
            }
            ps.addBatch();
        }
        return ps;
    }

    private Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

    private void close(PreparedStatement ps) throws SQLException {
        Connection conn = ps.getConnection();
        if (ps != null) {
            ps.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    private void closeRs(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }
}

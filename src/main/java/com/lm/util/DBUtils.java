package com.lm.util;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 10:03
 **/

import com.alibaba.druid.pool.DruidDataSource;
import com.lm.exception.SqlExecutorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {

    private static Logger log = LoggerFactory.getLogger(DBUtils.class);

    public static final String DEFAULT_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DEFAULT_URL = "jdbc:mysql://localhost:4000/test?useUnicode=true&characterEncoding=UTF-8&useAffectedRows=true";
    public static final String DEFAULT_USERNAME = "root";
    public static final String DEFAULT_PASSWORD = "";

    private static DruidDataSource druidDataSource = null;


    public static void config(String driver, String url, String username, String password) throws SQLException {
        druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(5);
        druidDataSource.setMinIdle(5);
        druidDataSource.setMaxActive(100);
        druidDataSource.setMaxWait(5000);
        druidDataSource.setUseUnfairLock(true);
        druidDataSource.setQueryTimeout(5);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setPoolPreparedStatements(false);
        test();
    }

    /**
     * 获取数据库连接
     * @return
     * @throws Exception
     */
    public static Connection openConnection() throws Exception {
        Connection conn = null;
        try {
            conn = druidDataSource.getConnection();
        } catch (Exception e) {
            log.info("获取数据库连接失败",e);
            throw new SqlExecutorException("获取数据库连接失败");
        }
        return conn;
    }

    public static void test() throws SQLException {
        Connection conn = null;
        try {
            conn = DBUtils.openConnection();
            if (conn != null) {
                log.info("数据库连接正常！");
            }
        } catch (Exception ex) {
            log.error("数据库连接异常！",ex);
            throw new SqlExecutorException("数据库连接异常！");
        }finally {
            if (conn != null){
                conn.close();
            }
        }
    }

    public static void release(ResultSet rs, Statement statement, Connection con) throws SQLException {
        /**
         * 释放资源
         */
        try {
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (con != null){
                con.close();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


}

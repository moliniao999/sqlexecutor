package com.lm.util;

import com.lm.exception.SqlExecutorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 10:11
 **/
public class Dao {

    Logger log = LoggerFactory.getLogger(Dao.class);

    private static PreparedStatement pst = null;

    private static ResultSet rs = null;

    //保存查询到的数据
    List list = null;

    /**
     * 查询数据库，获取一个User类型的list集合
     */
    public List select(String sql, Connection conn) throws SQLException {
        list = new ArrayList<String>();

        try {
            pst = conn.prepareStatement(sql);
            //执行sql
            rs = pst.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.release(rs, pst, conn);
        }
        return list;
    }


    public int update(String sql, Connection conn) throws Exception {

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);

            //执行sql语句
            int effect = ps.executeUpdate();
            return effect;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("sql执行错误", e);
            throw new SqlExecutorException("sql执行错误");
        } finally {
            DBUtils.release(null, ps, null);
        }
    }


}


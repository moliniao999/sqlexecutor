package com.lm.executor;

import com.lm.util.DBUtils;
import com.lm.util.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 15:00
 **/

public class ClientThread implements Runnable {
    Logger log = LoggerFactory.getLogger(ClientThread.class);

    String name;
    Connection conn;
    List<String> sqlList;
    Dao dao = new Dao();
    Random random = new Random();
    //存放执行结果
    public Map<String,List<Integer>> result;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public List<String> getSqlList() {
        return sqlList;
    }

    public void setSqlList(List<String> sqlList) {
        this.sqlList = sqlList;
    }

    public Dao getDao() {
        return dao;
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }

    public ClientThread(String name, List<String> sqlList,Map<String,List<Integer>> result) {
        this.name = name;
        this.sqlList = sqlList;
        this.result = result;
    }

    @Override
    public void run() {
        try {
            //随机休眠0-5毫秒,模拟真实场景
            Thread.sleep(random.nextInt(5));
            //获取数据库连接
            conn = DBUtils.openConnection();
            int effect = 0;
            List<Integer> resultOfThread = new ArrayList<>();
            for (String sql : sqlList) {
                //暂不支持select语句
                if (sql.trim().startsWith("select")) {
                    continue;
                }
                effect = dao.update(sql, conn);
                resultOfThread.add(effect);
                //随机休眠0-5毫秒,模拟真实场景
                Thread.sleep(random.nextInt(5));
            }
            result.put(name, resultOfThread);
            log.info("客户端开始执行sql结束: client = " + name +", sqlList = " + sqlList);

        } catch (Exception e) {
            log.error("线程执行错误:{}",name,e);
        }finally {
            try {
                DBUtils.release(null, null, conn);
            } catch (SQLException e) {
                log.error("释放连接错误",e);
            }
        }
    }
}

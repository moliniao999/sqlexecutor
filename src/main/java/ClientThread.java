import lombok.extern.slf4j.Slf4j;
import utils.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 15:00
 **/
@Slf4j
public class ClientThread implements Runnable {

    String name;
    Connection conn;
    List<String> sqls;
    Dao dao = new Dao();


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

    public List<String> getSqls() {
        return sqls;
    }

    public void setSqls(List<String> sqls) {
        this.sqls = sqls;
    }

    public Dao getDao() {
        return dao;
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }

    public ClientThread(String name, Connection conn, List<String> sqls) {
        this.name = name;
        this.conn = conn;
        this.sqls = sqls;

    }

    @Override
    public void run() {
        try {

            //Main.getCyclicBarrier().await();
            log.info("Thread:" + Thread.currentThread().getName() + "准备完毕,time:" + System.currentTimeMillis());



            Random random = new Random();
            for (String sql : sqls) {

                //Thread.sleep(random.nextInt(100));
                //log.info("客户端开始执行sql开始: client = " + name +", sql = " + sql);
                dao.update(sql, conn);
                log.info("客户端开始执行sql结束: client = " + name +", sql = " + sql);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                Dao.release(null, null, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

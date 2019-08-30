import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class ClientThread implements Runnable {
    Logger log = LoggerFactory.getLogger(ClientThread.class);

    String name;
    Connection conn;
    List<String> req;
    Dao dao = new Dao();
    Random random = new Random();

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

    public List<String> getReq() {
        return req;
    }

    public void setReq(List<String> req) {
        this.req = req;
    }

    public Dao getDao() {
        return dao;
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }

    public ClientThread(String name, List<String> req) {
        this.name = name;
        this.req = req;

    }

    @Override
    public void run() {
        try {

            //Main.getCyclicBarrier().await();
            log.info("Thread:" + Thread.currentThread().getName() + "开始执行,time:" + System.currentTimeMillis());
            Thread.sleep(random.nextInt(5));

            conn = DBUtils.openConnection();

            for (String sql : req) {
                log.info("客户端开始执行sql开始: client = " + name +", sql = " + sql);
                dao.update(sql, conn);
                //log.info("客户端开始执行sql结束: client = " + name +", sql = " + sql);
                Thread.sleep(random.nextInt(5));
            }

        } catch (Exception e) {
            log.error("执行错误",e);
        }finally {
            try {
                Dao.release(null, null, conn);
            } catch (SQLException e) {
                log.error("释放连接错误",e);
            }
        }
    }
}

import utils.Dao;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 15:00
 **/
public class Client implements Runnable {

    String name;
    Connection conn;
    List<String> sqls;
    Dao dao = new Dao();

    public Client(String name, Connection conn, List<String> sqls, CyclicBarrier cyclicBarrier) {
        this.name = name;
        this.conn = conn;
        this.sqls = sqls;

    }

    @Override
    public void run() {
        try {

            Main.getCyclicBarrier().await();
            System.out.println("Thread:" + Thread.currentThread().getName() + "准备完毕,time:" + System.currentTimeMillis());


            if (sqls == null || sqls.isEmpty()) {
                System.out.println("sql is empty");
                return;
            }
            for (String sql : sqls) {
                //System.out.println("客户端开始执行sql开始: client = " + name +", sql = " + sql);
                dao.update(sql, conn);
                System.out.println("客户端开始执行sql结束: client = " + name +", sql = " + sql);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //try {
            //    Dao.release(null, null, conn);
            //} catch (SQLException e) {
            //    e.printStackTrace();
            //}
        }
    }
}

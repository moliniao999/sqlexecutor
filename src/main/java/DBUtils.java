

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 10:03
 **/

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {

    private static Logger log = LoggerFactory.getLogger(DBUtils.class);

    private static DruidDataSource druidDataSource = null;


    public static void config(String driver, String url, String username, String password) {
        //DBUtils.loadDriver();
        druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(5);
        druidDataSource.setMinIdle(1);
        druidDataSource.setMaxActive(100);
        druidDataSource.setMaxWait(60000);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setPoolPreparedStatements(false);
    }

    public static Connection openConnection() throws Exception {

        Connection conn = null; //连接数据库
        try {
            conn = druidDataSource.getConnection();
        } catch (SQLException e) {
            log.info("获取数据库连接失败",e);
            throw new Exception("获取数据库连接失败");
        }

        return conn;
    }

    public static void main(String[] args) {

        try {
            Connection conn = DBUtils.openConnection();

            if (conn != null) {
                log.info("数据库连接正常！");
            } else {
                log.info("数据库连接异常！");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

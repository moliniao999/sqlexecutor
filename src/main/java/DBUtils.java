

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 10:03
 **/


import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
@Slf4j
public class DBUtils {

    public static String driver;
    public static String url;
    public static String username;
    public static String password;

    //加载驱动
    public static void loadDriver() {
        try {
            Class.forName(driver);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //
    ////单例模式返回数据库连接对象，供外部调用
    //public static Connection getConnection() throws Exception {
    //    if (conn == null) {
    //        conn = DriverManager.getConnection(url, username, password); //连接数据库
    //        return conn;
    //    }
    //    return conn;
    //}

    public static Connection openConnection() throws Exception {

        Connection conn = null; //连接数据库
        try {
            conn = DriverManager.getConnection(url, username, password);
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

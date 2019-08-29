package utils;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 10:03
 **/


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public static Connection openConnection() {

        Connection conn = null; //连接数据库
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取连接失败");
            return null;
        }

        return conn;
    }

    public static void main(String[] args) {

        try {
            Connection conn = DBUtils.openConnection();

            if (conn != null) {
                System.out.println("数据库连接正常！");
            } else {
                System.out.println("数据库连接异常！");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //public static void configure(String driver, String url, String username, String password) {
    //    DBUtils.driver = driver;
    //    DBUtils.url = url;
    //    DBUtils.username = username;
    //    DBUtils.password = password;
    //}
}

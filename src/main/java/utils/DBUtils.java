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

    private static final String driver = "com.mysql.jdbc.Driver";

    private static final String url = "jdbc:mysql://localhost:3306/santaba?useUnicode=true&characterEncoding=UTF-8";

    private static final String username = "root";
    private static final String password = "";

    private static Connection conn = null;

    //静态代码块负责加载驱动
    static {
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
}

package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 10:11
 **/
public class Dao {


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

            // 遍历处理结果集
            while (rs.next()) {
                int id = rs.getInt("id");
                String uuid = rs.getString("id");
                String name = rs.getString("name");
                System.out.println("id = " + id + ", name = " + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(rs, pst, conn);
        }
        return list;
    }


    public void update(String sql, Connection conn) throws Exception{

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);

            //执行sql语句
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            release(null, ps, null);
        }
    }

    public static void release(ResultSet rs,Statement statement, Connection con) throws SQLException{
        /**
         * 释放资源
         */
        try {
            if(rs != null)
                rs.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if(statement != null)
                statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(con != null)
                con.close();
        }
    }

}



/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 14:38
 **/

public class Main {


    public static void main(String[] args) throws InterruptedException {
        //log.info(System.getProperty("user.dir"));//user.dir指定了当前的路径
        //String path = "/Users/weili/IdeaProjects/home/sqlexecutor/sql";
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/santaba?useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "";

        int threadNum = 100;
        int loopNum = 100;
        int loopDelayTime = 50;
        String path = "sql";

        /** ---------- */
        //初始化数据源
        DBUtils.config(driver, url, username, password);
        //设置执行计划
        TestPlan testPlan = new TestPlan();
        testPlan.setThreadNum(threadNum);
        testPlan.setLoopNum(loopNum);
        testPlan.setLoopDelayTime(loopDelayTime);
        testPlan.initSqlCache(path);
        //初始化sql执行器
        SqlExecutor executor = new StandardSqlExecutor();
        executor.configure(testPlan);
        //执行任务
        executor.execute();

    }


}

import utils.DBUtils;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 14:38
 **/
public class Main {


    public static void main(String[] args) throws SqlExecutorException, InterruptedException {
        //System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径
        String path = "/Users/weili/IdeaProjects/home/sqlexecutor/sql";
        int threadNum = 3;
        int loopNum = 1;
        int loopDelayTime = 50;

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/santaba?useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "";


        /** ---------- */
        //初始化数据源
        initDBUtils(driver, url, username, password);
        //设置执行计划
        TestPlan testPlan = new TestPlan();
        testPlan.setThreadNum(threadNum);
        testPlan.setLoopNum(loopNum);
        testPlan.setLoopDelayTime(loopDelayTime);
        testPlan.initSqlCache(path);
        //初始化sql执行器
        SqlExecutor executor = new RandomSqlExecutor();
        executor.configure(testPlan);
        //执行sql
        executor.execute();


    }

    private static void initDBUtils(String driver, String url, String username, String password) {
        DBUtils.driver = driver;
        DBUtils.url = url;
        DBUtils.username = username;
        DBUtils.password = password;
        DBUtils.loadDriver();
    }


}

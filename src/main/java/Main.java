/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 14:38
 **/
public class Main {



    public static void main(String[] args) throws SqlExecutorException, InterruptedException {
        String path = "/Users/weili/IdeaProjects/home/sqlexecutor/sql";
        path = "sql";
        System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径

        TestPlan testPlan = new TestPlan();
        testPlan.setThreadNum(3);
        testPlan.setLoopNum(1);
        testPlan.setLoopDelayTime(50);
        testPlan.initSqlCache(path);
        //初始化sql执行器
        SqlExecutor executor = new RandomSqlExecutor();
        executor.configure(testPlan);
        //执行sql
        executor.execute();


    }







}

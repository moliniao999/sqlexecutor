import utils.DBUtils;

import java.util.Date;
import java.util.List;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 13:31
 **/
public class RandomSqlExecutor implements SqlExecutor, Runnable {


    public TestPlan testPlan;

    public volatile boolean running;

    public TestPlan getTestPlan() {
        return testPlan;
    }

    @Override
    public void configure(TestPlan testPlan) {
        this.testPlan = testPlan;
    }

    @Override
    public void execute() throws SqlExecutorException, InterruptedException {

        long now = System.currentTimeMillis();
        System.out.println("Starting the test on localhost " + " @ " + new Date(now) + " (" + now + ")");

        Thread thread = new Thread(this);
        thread.start();
        //等待执行结束
        thread.join();
        System.out.println("主线程执行结束");

    }


    @Override
    public void run() {

        running = true;
        try {
            List<List<String>/*sql list*/> sqlCache = getTestPlan().getSqlCache();
            int threadNum = getTestPlan().getThreadNum();
            int loopNum = getTestPlan().getLoopNum();
            int loopDelayTime = getTestPlan().getLoopDelayTime();
            int mod;
            List<String> sqls;
            for (int j = 1; running && j <= loopNum; j++) {

                for (int i = 1; running && i <= threadNum; i++) {
                    mod = i % sqlCache.size();
                    sqls = sqlCache.get(mod);
                    if (sqls == null || sqls.isEmpty()) {
                        System.out.println("sql is empty");
                        continue;
                    }
                    ClientThread client = new ClientThread("loop" + j + ", client" + i, DBUtils.openConnection(), sqls);
                    Thread newThread = new Thread(client, client.getName());
                    newThread.start();
                }

                Thread.sleep(loopDelayTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() {
        running = false;
    }
}

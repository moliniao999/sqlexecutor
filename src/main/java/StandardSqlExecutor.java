import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 13:31
 **/

public class StandardSqlExecutor implements SqlExecutor {

    Logger log = LoggerFactory.getLogger(StandardSqlExecutor.class);

    public TestPlan testPlan;

    public volatile boolean running;

    public TestPlan getTestPlan() {
        return testPlan;
    }

    public ExecutorService executor = Executors.newCachedThreadPool();

    public static Map<String,List<String>> result = new ConcurrentHashMap<>();

    @Override
    public void configure(TestPlan testPlan) {
        this.testPlan = testPlan;
    }

    @Override
    public void execute() throws InterruptedException {

        long start = System.currentTimeMillis();
        log.info("Starting the test on localhost " + " @ " + start );

        running = true;
        try {
            List<List<String>/*sql list*/> sqlRequest = getTestPlan().getSqlRequest();
            int threadNum = getTestPlan().getThreadNum();
            int loopNum = getTestPlan().getLoopNum();
            int loopDelayTime = getTestPlan().getLoopDelayTime();
            int mod;
            List<String> req;
            for (int j = 1; running && j <= loopNum; j++) {

                for (int i = 1; running && i <= threadNum; i++) {
                    mod = i % sqlRequest.size();
                    req = sqlRequest.get(mod);
                    if (req == null || req.isEmpty()) {
                        log.info("sql is empty");
                        continue;
                    }
                    ClientThread client = new ClientThread("thread-loop" + j + "-client" + i, req);
                    executor.execute(client);
                }
                Thread.sleep(loopDelayTime);
            }
            executor.shutdown();
            log.info("任务执行结束");
        } catch (Exception e) {
            log.error("执行错误",e);
            stop();
        }
        //System.out.println("子线程执行时长：" + (end - start));
    }

    @Override
    public void stop() {
        running = false;
        if (executor != null) {
            executor.shutdown();
        }
    }
}

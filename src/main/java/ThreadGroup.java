import utils.DBUtils;

import java.util.List;

/**
 * @program: sqlexecutor
 * @description: 线程组
 * @author: weili
 * @create: 2019-08-29 11:06
 **/
public class ThreadGroup implements Runnable {


    RandomSqlExecutor randomSqlExecutor;

    public ThreadGroup(RandomSqlExecutor randomSqlExecutor) {
        this.randomSqlExecutor = randomSqlExecutor;
    }


    @Override
    public void run() {
        try {
            List<List<String>/*sql list*/> sqlCache = randomSqlExecutor.getTestPlan().getSqlCache();
            int threadNum = randomSqlExecutor.getTestPlan().getThreadNum();
            int loopNum = randomSqlExecutor.getTestPlan().getLoopNum();
            int loopDelayTime = randomSqlExecutor.getTestPlan().getLoopDelayTime();
            int mod;
            List<String> sqls;
            for (int j = 1; randomSqlExecutor.running && j <= loopNum; j++) {

                for (int i = 1; randomSqlExecutor.running && i <= threadNum; i++) {
                    mod = i % sqlCache.size();
                    sqls = sqlCache.get(mod);
                    if (sqls == null || sqls.isEmpty()) {
                        System.out.println("sql is empty");
                        continue;
                    }
                    ClientThread client = new ClientThread("loop"+j+", client"+i, DBUtils.openConnection(),sqls);
                    Thread newThread = new Thread(client, client.getName());
                    newThread.start();
                }

                Thread.sleep(loopDelayTime);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}

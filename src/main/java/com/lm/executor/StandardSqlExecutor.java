package com.lm.executor;

import com.lm.config.Context;
import com.lm.config.SqlTestPlan;
import com.lm.exception.SqlExecutorException;
import com.lm.util.Result;
import com.lm.util.SqlFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 13:31
 **/

public class StandardSqlExecutor implements SqlExecutor {

    Logger log = LoggerFactory.getLogger(StandardSqlExecutor.class);

    public Context<SqlTestPlan> context;

    public static volatile boolean running;

    //创建一个线程池来执行
    public ExecutorService threadPool = new ThreadPoolExecutor(10, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());



    //存放每个线程的执行结果
    public Map<String/*thread name*/, String/*result*/> data = new ConcurrentHashMap<>();

    public Context getContext() {
        return context;
    }

    @Override
    public void configure(Context context) {
        this.context = context;
    }

    @Override
    public Result execute() throws InterruptedException {

        if (running) {
            return new Result();
        }
        long start = System.currentTimeMillis();
        Result result = new Result();
        List<List<String>/*sql list*/> sqlRequest;
        running = true;
        try {
            //获取sql文件内容
            sqlRequest = getSqlList(context.getTestPlan().getPath());
            int threadNum = context.getTestPlan().getThreadNum();
            int loopNum = context.getTestPlan().getLoopNum();
            int loopDelayTime = context.getTestPlan().getLoopDelayTime();
            int mod;
            List<String> req;
            for (int j = 1; running && j <= loopNum; j++) {

                for (int i = 1; running && i <= threadNum; i++) {
                    //客户端线程依次获取对应的sql
                    mod = i % sqlRequest.size();
                    req = sqlRequest.get(mod);
                    if (req == null || req.isEmpty()) {
                        log.info("sql is empty");
                        continue;
                    }
                    ClientThread client = new ClientThread("thread-loop" + j + "-client" + i, req, data);
                    threadPool.execute(client);
                }
                Thread.sleep(loopDelayTime);
            }
            threadPool.shutdown();
            //等待工作线程执行完毕
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
            stop();
            log.info("任务执行结束");
            //设置执行结果
            result.setData(data);
        } catch (SqlExecutorException ex) {
            stop();
            throw ex;
        } catch (Exception e) {
            log.error("执行错误", e);
            stop();
        }
        System.out.println("子线程执行时长：" + (System.currentTimeMillis() - start));
        return result;
    }

    @Override
    public void stop() {
        running = false;
    }


    /**
     * 加载sql文件数据
     *
     * @param path
     */
    public List<List<String>/*sql list*/>  getSqlList(String path) {
        List<List<String>/*sql list*/> sqlRequest = new ArrayList<>();
        List<File> files = SqlFileReader.getListFiles(path);
        if (files.isEmpty()) {
            throw new SqlExecutorException("未找到sql文件");
        }
        files.forEach(file -> {
            List<String> sqls = SqlFileReader.readFileIntoList(file.getAbsolutePath());
            sqlRequest.add(sqls);
        });
        return sqlRequest;
    }
}

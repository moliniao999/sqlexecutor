import java.util.Date;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 13:31
 **/
public class RandomSqlExecutor implements SqlExecutor,Runnable {


    public TestPlan testPlan;

    public volatile boolean running;

    public TestPlan getTestPlan() {
        return testPlan;
    }

    @Override
    public void configure(TestPlan testPlan) {
        this.testPlan = testPlan;
    }

    public void execute() throws SqlExecutorException, InterruptedException {

        long now = System.currentTimeMillis();
        System.out.println("Starting the test on localhost " + " @ " + new Date(now) + " (" + now + ")");

        running = true;
        Thread thread = new Thread(new ThreadGroup(this));
        thread.start();
        //等待执行结束
        thread.join();
        System.out.println("主线程执行结束");

    }

    @Override
    public void stop() {
        if (running) {
            running = false;
            Thread stopThread = new Thread(this);
            stopThread.start();
        }
    }


    @Override
    public void run() {

    }
}

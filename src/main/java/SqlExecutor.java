/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 13:31
 **/
public interface SqlExecutor {

    void configure(TestPlan testPlan);

    void execute() throws SqlExecutorException, InterruptedException;

    void stop();

}

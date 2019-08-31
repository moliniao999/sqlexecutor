package com.lm.executor;

import com.lm.config.TestPlan;
import com.lm.util.Result;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 13:31
 **/
public interface SqlExecutor {

    void configure(TestPlan testPlan);

    Result execute() throws InterruptedException;

    void stop();

}

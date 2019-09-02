package com.lm.executor;

import com.lm.config.Context;
import com.lm.util.Result;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 13:31
 **/
public interface SqlExecutor<T> {

    void configure(Context<T> context);

    Result execute() throws InterruptedException;

    void stop();

}

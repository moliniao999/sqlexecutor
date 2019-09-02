package com.lm.config;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-09-02 10:17
 **/
public class Context<T> {
    private T testPlan;

    public Context(T testPlan) {
        this.testPlan = testPlan;
    }

    public T getTestPlan() {
        return testPlan;
    }

    public void setTestPlan(T testPlan) {
        this.testPlan = testPlan;
    }
}

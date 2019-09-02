package com.lm.config;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-29 14:06
 **/
public class SqlTestPlan extends TestPlan{

    private String name;
    //线程数
    private  int threadNum = 2;
    //循环次数
    private int loopNum = 3;
    //循环间隔时间,单位毫秒
    private int loopDelayTime = 50;
    // sql文件存放路径
    private String path = "sql";



    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public int getLoopNum() {
        return loopNum;
    }

    public void setLoopNum(int loopNum) {
        this.loopNum = loopNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getLoopDelayTime() {
        return loopDelayTime;
    }

    public void setLoopDelayTime(int loopDelayTime) {
        this.loopDelayTime = loopDelayTime;
    }

}

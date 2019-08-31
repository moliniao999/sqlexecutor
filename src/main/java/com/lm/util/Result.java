package com.lm.util;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-31 14:44
 **/
public class Result<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

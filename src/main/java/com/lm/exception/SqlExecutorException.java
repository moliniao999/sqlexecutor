package com.lm.exception;/*
 * Copyright (c) 2014 www.diligrp.com All rights reserved.
 * 本软件源代码版权归----所有,未经许可不得任意复制与传播.
 */


public class SqlExecutorException extends RuntimeException{
	private String code = "500";
	private String errorData;
	public SqlExecutorException() {
		super();
	}

	public SqlExecutorException(String message) {
		super(message);
	}

	public SqlExecutorException(String message, Throwable cause) {
		super(message, cause);
	}

	public SqlExecutorException(Throwable cause) {
		super(cause);
	}

	public SqlExecutorException(String code, String message) {
	    super(message);
	    this.code=code;
    }

	public SqlExecutorException(String code, String errorData, String message) {
        super(message);
        this.code=code;
        this.errorData=errorData;
    }
	
	
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    
    public String getErrorData() {
        return errorData;
    }

    
    public void setErrorData(String errorData) {
        this.errorData = errorData;
    }

    @Override
    public String toString() {
        return "com.lm.exception.SqlExecutorException [code=" + getCode() + ", message=" + getMessage()
                + ", cause=" + getCause() + "]";
    }
}

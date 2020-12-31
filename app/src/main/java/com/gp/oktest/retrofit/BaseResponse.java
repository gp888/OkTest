package com.gp.oktest.retrofit;

public class BaseResponse<T> {
    public int code;
    public String msg;
    public T data;
}

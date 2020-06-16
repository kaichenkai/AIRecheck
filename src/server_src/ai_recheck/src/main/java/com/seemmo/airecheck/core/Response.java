package com.seemmo.airecheck.core;

import com.alibaba.fastjson.JSON;
import java.util.function.Consumer;

/**
 * 统一API响应结果封装
 */
public class Response<T> {
    private int code;
    private String message;
    private T data;

    public Response setCode(int resultCode) {
        this.code = resultCode;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Response setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Response setData(T data) {
        this.data = data;
        return this;
    }

    public boolean success() {
        return code == ResponseGenerator.SUCCESS_CODE;
    }

    public void onFail(Consumer<String> consumer) {
        if (!success()) {
            consumer.accept(this.message);
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

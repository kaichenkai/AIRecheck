package com.seemmo.airecheck.core;

import com.alibaba.fastjson.JSON;
import java.util.function.Consumer;

/**
 * 统一API响应结果封装
 */
public class Result<T> {
    public static final int SUCCESS = 0;
    private int code;
    private String message;
    private T data;

    public Result setCode(int resultCode) {
        this.code = resultCode;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    public boolean success() {
        return code == SUCCESS;
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

package com.seemmo.airecheck.core;

import com.alibaba.fastjson.JSONObject;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "success";
    private static final String CODE = "code";
    private static final String MESSAGE = "message";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS.getCode())
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> Result<T> genSuccessResult(T data) {
        return new Result()
                .setCode(ResultCode.SUCCESS.getCode())
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL.getCode())
                .setMessage(message);
    }

    public static <T> Result<T> genFailResult(String message, T data) {
        return genFailResult(ResultCode.FAIL.getCode(), message, data);
    }

    public static <T> Result<T> genFailResult(int code, String message, T data) {
        return new Result()
                .setCode(code)
                .setMessage(message).setData(data);
    }

    public static <T> Result<T> genFailResult(int code, String message) {
        return new Result()
                .setCode(code)
                .setMessage(message);
    }

    public static Result genNoNataResult(String message) {
        return new Result()
                .setCode(ResultCode.NODATA.getCode())
                .setMessage(message);
    }

    public static Result parse(JSONObject response) {
        return new Result()
                .setCode(response.getIntValue(CODE))
                .setMessage(response.getString(MESSAGE));
    }
}

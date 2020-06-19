package com.seemmo.airecheck.core;

import com.alibaba.fastjson.JSONObject;

/**
 * 响应报文生成工具类
 */
public class ResponseGenerator {
    public static final int SUCCESS_CODE = 0;
    public static final int None = -1;//未知
    public static final int NODATA = 3;//无数据
    public static final int UNAUTHORIZED = 403;//未认证（签名错误）
    public static final int NOT_FOUND = 404;//接口不存在
    public static final int USER_UNABLE = 409;//用户账号不可用，需修改密码
    public static final int INTERNAL_SERVER_ERROR = 500;//服务器内部错误
    //
    private static final String DEFAULT_SUCCESS_MESSAGE = "success";
    private static final String DEFAULT_NODATA_MESSAGE = "no data";
    private static final String CODE = "code";
    private static final String MESSAGE = "message";

    public static Response genSuccessResp() {
        return new Response().setCode(SUCCESS_CODE).setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> Response<T> genSuccessResp(T data) {
        return new Response()
                .setCode(SUCCESS_CODE)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Response genErrorResp(ExceptionInfo exceptionInfo) {
        return new Response()
                .setCode(exceptionInfo.code)
                .setMessage(exceptionInfo.message);
    }

    public static Response genErrorResp(int code, String message) {
        return new Response()
                .setCode(code)
                .setMessage(message);
    }

    public static Response genNoNataResp() {
        return new Response()
                .setCode(NODATA)
                .setMessage(DEFAULT_NODATA_MESSAGE);
    }

    public static Response genResp(int code, String message) {
        return new Response()
                .setCode(code)
                .setMessage(message);
    }

    public static <T> Response<T> genResp(int code, String message, T data) {
        return new Response()
                .setCode(code)
                .setMessage(message)
                .setData(data);
    }

    public static Response parseJson(JSONObject response) {
        return new Response()
                .setCode(response.getIntValue(response.getString(CODE)))
                .setMessage(response.getString(MESSAGE));
    }
}

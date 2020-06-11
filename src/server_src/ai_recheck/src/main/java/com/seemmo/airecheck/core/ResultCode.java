package com.seemmo.airecheck.core;

/**
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {
    //未知
    None(-1),
    SUCCESS(0),//请求成功
    FAIL(1),//失败
    NODATA(3),//无数据
    UNAUTHORIZED(403),//未认证（签名错误）
    USER_UNABLE(409),//用户账号不可用，需修改密码
    NOT_FOUND(404),//接口不存在
    INTERNAL_SERVER_ERROR(500);//服务器内部错误

    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

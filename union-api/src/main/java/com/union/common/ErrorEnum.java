package com.union.common;/*
 * @创建人：ZS
 * @创建时间：
 * @描述：
 */

public enum ErrorEnum {
    SUSSESS("200","请求成功"),
    ERROR("500","请求失败"),
    CHECK_ERROR("10001","校验失败"),
    SOURCE_NO_HAVE("10002","来源不存在")
    ;

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

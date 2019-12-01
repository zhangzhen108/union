package com.union.common;

public enum CommonStatusEnum{
    enable(0,"可用"),
    unable(1,"不可用");
    ;
    private Integer status;

    private String msg;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    CommonStatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}

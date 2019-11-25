package com.union.common;

/**
 * 来源
 */
public enum SourceEnum {
    jdd(1,"product-pdd","拼多多"),
    jd(2,"product-jd","京东"),
    tb(3,"product-tb","淘宝"),
    ;

    private Integer scource;
    private String code;
    private String msg;

    SourceEnum(Integer scource, String code, String msg) {
        this.scource = scource;
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getScource() {
        return scource;
    }

    public void setScource(Integer scource) {
        this.scource = scource;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

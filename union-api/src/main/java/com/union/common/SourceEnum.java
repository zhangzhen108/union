package com.union.common;

/**
 * 来源
 */
public enum SourceEnum {
    jdd(1l,"product-pdd","拼多多"),
    tb(2l,"product-tb","淘一淘"),
    jd(3l,"product-jd","京东"),
    mgj(4l,"product-mgj","蘑菇街"),
    ;

    private Long id;
    private String code;
    private String msg;

    SourceEnum(Long id, String code, String msg) {
        this.id = id;
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

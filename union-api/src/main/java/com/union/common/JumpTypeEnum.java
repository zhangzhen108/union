package com.union.common;

public enum JumpTypeEnum {
    COPY(1,"复制"),
    JUMP(2,"跳转"),
    ;


    JumpTypeEnum(Integer type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    private Integer type;

    private String msg;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

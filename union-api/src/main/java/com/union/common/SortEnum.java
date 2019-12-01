package com.union.common;

public enum  SortEnum {
    ASC(1,"正叙排序"),
    DESC(-1,"倒叙排序"),
    ;

    SortEnum(Integer sort, String msg) {
        this.sort = sort;
        this.msg = msg;
    }

    private Integer sort;

    private String msg;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

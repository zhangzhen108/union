package com.union.common;

public enum  SortEnum {
    ASC(1,"asc","正叙排序"),
    DESC(-1,"desc","倒叙排序"),
    ;

    SortEnum(Integer sort,String code, String msg) {
        this.sort = sort;
        this.code = code;
        this.msg = msg;
    }
    private String code;

    private Integer sort;

    private String msg;

    public Integer getSort() {
        return sort;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

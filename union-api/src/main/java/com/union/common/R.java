package com.union.common;

public class R<T> {
    private String code;

    private String msg;

    private T data;
    public static R ok(){
        R r=new R();
        r.code=ErrorEnum.SUSSESS.getCode();
        r.msg=ErrorEnum.SUSSESS.getMsg();
        return r;
    }
    public static <T> R creatR(String errorCode,String errorMsg){
        R r=new R();
        r.code=errorCode;
        r.msg=errorMsg;
        return r;
    }
    public static <T> R creatR(T o,ErrorEnum errorEnum){
        R r=new R();
        r.code=errorEnum.getCode();
        r.msg=errorEnum.getMsg();
        r.data=o;
        return r;
    }
    private static <T> R ok(T o){
        R r=new R();
        r.code=ErrorEnum.SUSSESS.getCode();
        r.msg=ErrorEnum.SUSSESS.getMsg();
        r.data=o;
        return r;
    }
    public static <T> R fail(){
        R r=new R();
        r.code=ErrorEnum.ERROR.getCode();
        r.msg=ErrorEnum.ERROR.getMsg();
        return r;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

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
}

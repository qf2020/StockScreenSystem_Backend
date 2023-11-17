package com.cjk.bakend.demo.pojo;

public class Result {
    private int code;
    private String msg;
    private Object data;

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public Object getData() {
        return data;
    }
    public static Result succ(Object data) {
        return succ(200, "操作成功", data);
    }

    public static Result fail(String msg) {
        return fail(400,"操作失败！" , msg);
    }

    public static Result succ (int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result fail (int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}

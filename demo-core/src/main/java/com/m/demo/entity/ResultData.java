package com.m.demo.entity;

import cn.hutool.json.JSONUtil;

/**
 * author:M
 * describe:
 * date:2020/9/1 18:30
 */
public class ResultData {
    private int code;
    private String msg;
    private Object  data;

    public ResultData(){

    }

    public ResultData(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr( this );
    }
}

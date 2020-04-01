package com.lennon.cn.utill.bean;

import java.io.Serializable;
import java.util.Collection;

import cn.droidlover.xdroidmvp.net.IModel;

/**
 * Created by dingyi on 2016/11/30.
 */

public class HttpEntity<T> implements IModel, Serializable {
    private String msg;
    private String code;
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return "0000".equals(code);
    }

    @Override
    public String getErrorCode() {
        return code;
    }

    @Override
    public boolean isNull() {
        if (!"0000".equals(code)) {
            return data == null && !"1000".equals(code);
        }
        return false;
    }

    @Override
    public boolean isAuthError() {
        return "1000".equals(code);
    }

    @Override
    public boolean isBizError() {
        return !"0000".equals(code);
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }
}

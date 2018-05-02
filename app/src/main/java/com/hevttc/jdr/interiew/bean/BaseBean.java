package com.hevttc.jdr.interiew.bean;

import java.io.Serializable;

/**
 * Created by hegeyang on 2017/12/21.
 */

public class BaseBean<T> implements Serializable{



    private int code;
    private String msg;
    private boolean success;
    private T data;

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

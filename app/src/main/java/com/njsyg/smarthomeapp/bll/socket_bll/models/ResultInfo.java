package com.njsyg.smarthomeapp.bll.socket_bll.models;

import java.util.List;

/**
 * Created by user on 2016/6/29.
 */
public class ResultInfo<T> {
    public Boolean getState() {
        return State;
    }

    public void setState(Boolean state) {
        this.State = state;
    }



    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        this.Msg = msg;
    }

    private Boolean State;


    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    private T Data;
    private String Msg;
}

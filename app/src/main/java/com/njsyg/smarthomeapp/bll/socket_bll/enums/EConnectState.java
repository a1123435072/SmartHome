package com.njsyg.smarthomeapp.bll.socket_bll.enums;

/**
 * socket连接状态
 * Created by zz on 2016/10/21.
 */
public enum  EConnectState {
    /**
     * 连接正常
     */
    Normal,
    /**
     * 连接异常
     */
    Exception,
    /**
     * 连接中断
     */
    InterruptOrClose;
}

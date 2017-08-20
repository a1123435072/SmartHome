package com.njsyg.smarthomeapp.bll.socket_bll;

import java.util.TimerTask;

/**
 * Created by zz on 2016/10/21.
 */
public class SocketTimerTask extends TimerTask {

    private CallBack callBack;
    public SocketTimerTask(CallBack callBack)
    {
        this.callBack = callBack;
    }

    @Override
    public void run() {
        callBack.keepAlive();
    }
}


package com.njsyg.smarthomeapp.bll.socket_bll;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.EConnectState;

/**
 * Created by zz on 2016/10/21.
 */
public interface CallBack {
    /**
     * 保持长连接
     * 连接在线，发心跳验证
     * 连接不在线，重新连接
     */
    public void keepAlive();

    /**
     * 更新连接状态
     * @param state 连接状态
     */
    public void updConnectState(EConnectState state);

    /**
     * 获取当前连接状态
     */
    public boolean getConnectState();

    /**
     * 读取数据
     * */
    public void  readData(byte[] data);
}

package com.njsyg.smarthomeapp.bll.socket_bll.enums;

import java.io.Serializable;

/**
 * socket服务端类型
 * Created by zz on 2016/10/21.
 */
public enum EServerType implements Serializable {
    /**
     * 公网服务器
     */
    PublicServer,
    /**
     * 家庭服务器
     */
    HomeServer,
}

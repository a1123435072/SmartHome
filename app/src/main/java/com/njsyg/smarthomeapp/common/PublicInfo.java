package com.njsyg.smarthomeapp.common;

import com.njsyg.smarthomeapp.bll.socket_bll.models.T_User;
import com.njsyg.smarthomeapp.bll.socket_bll.models.View_HServerAndUser;

import java.util.List;

/**
 * Created by zz on 2016/10/21.
 */
public class PublicInfo {
    public  static T_User currentLoginUser;
    public static List<View_HServerAndUser> currentUserHServerList;
}

package com.njsyg.smarthomeapp.bll.db_bll;


import com.njsyg.smarthomeapp.dal.DbCore;

import java.util.List;

import njsyg.greendao.db.dao.DB_HServerAndUserDao;
import njsyg.greendao.db.model.DB_HServerAndUser;

/**
 * Created by user on 2016/7/19.
 */
public class DB_HServerAndUser_BLL {
    private static DB_HServerAndUserDao db_hServerAndUserDao;

    static {
        if (db_hServerAndUserDao == null) {
            db_hServerAndUserDao = DbCore.getDaoSession().getDB_HServerAndUserDao();
        }
    }

    /**
     * 新增用户家庭服务器信息*
     */
    public static Long Add(DB_HServerAndUser item) {
        return db_hServerAndUserDao.insert(item);
    }
    /**
     * 修改用户家庭服务器信息*
     */
    public static void Update(DB_HServerAndUser item) {
        db_hServerAndUserDao.update(item);
    }

    /**
     * 通过手机号获取家庭服务器
     */
    public static List<DB_HServerAndUser> GetListByPhoneNum(String phonenum) {
        List list = db_hServerAndUserDao.queryBuilder().where(DB_HServerAndUserDao.Properties.User_phoneNumber.eq(phonenum)).list();
        if (list != null) {
            return (List<DB_HServerAndUser>) list;
        } else return null;
    }
    /**
     * 通过手机号获取家庭服务器
     */
    public static DB_HServerAndUser GetOne(String fserverSN, String phonenum) {
        List list = db_hServerAndUserDao.queryBuilder().where(DB_HServerAndUserDao.Properties.Hserver_sn.eq(fserverSN), DB_HServerAndUserDao.Properties.User_phoneNumber.eq(phonenum)).list();
        if (list != null && list.size() > 0) {
            return (DB_HServerAndUser) list.get(0);
        } else {
            return null;
        }
    }

    public static void Delete(Long key)
    {
        db_hServerAndUserDao.deleteByKey(key);
    }
}

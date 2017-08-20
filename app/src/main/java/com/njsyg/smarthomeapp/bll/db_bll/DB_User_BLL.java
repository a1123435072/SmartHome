package com.njsyg.smarthomeapp.bll.db_bll;


import com.njsyg.smarthomeapp.dal.DbCore;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
import njsyg.greendao.db.dao.DB_UserDao;
import njsyg.greendao.db.model.DB_User;

/**
 * Created by user on 2016/7/19.
 */
public class DB_User_BLL {
    private static DB_UserDao db_userDao;

    static {
        if (db_userDao == null) {
            db_userDao = DbCore.getDaoSession().getDB_UserDao();
        }
    }

    /**
     * 保存用户信息*
     */
    public static Long Add(DB_User db_user) {
        return db_userDao.insert(db_user);
    }

    /**
     * 保存用户信息*
     */
    public static Long InserOrReplace(DB_User db_user) {
        return db_userDao.insertOrReplace(db_user);
    }

    /**
     * 更新用户信息*
     */
    public static void Update(DB_User db_user) {
        db_userDao.update(db_user);
    }

    /**
     * 删除用户信息*
     */
    public static void Delete(long key) {
        db_userDao.deleteByKey(key);
    }

    /**
    *获取*
     */
    public static DB_User GetOne(long key) {
        return db_userDao.load(key);
    }

    /**
    *获取*
     */
    public static DB_User GetOne(String phoneNum) {
        QueryBuilder qb = db_userDao.queryBuilder();
        qb.where(DB_UserDao.Properties.User_phoneNumber.eq(phoneNum));
        List list = qb.list();
        if (list != null && list.size() > 0) {
            return (DB_User) list.get(0);
        } else {
            return null;
        }
    }
}

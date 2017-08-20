package com.njsyg.smarthomeapp.bll.db_bll;


import com.njsyg.smarthomeapp.dal.DbCore;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
import njsyg.greendao.db.dao.DB_HServerKeyDao;
import njsyg.greendao.db.model.DB_HServerKey;

/**
 * Created by user on 2016/7/19.
 */
public class DB_HServerKey_BLL {

    private static DB_HServerKeyDao db_HServerKeyDao;

    static {
        if (db_HServerKeyDao == null) {
            db_HServerKeyDao = DbCore.getDaoSession().getDB_HServerKeyDao();
        }
    }

    /**
     * 获取本家庭服务器秘钥*
     */
    public static List<DB_HServerKey> GetList(String fserverSN, int keyType) {
        QueryBuilder qb = db_HServerKeyDao.queryBuilder();
        qb.where(DB_HServerKeyDao.Properties.Hserver_sn.eq(fserverSN), DB_HServerKeyDao.Properties.Keytype.eq(keyType));
        List list = qb.list();
        return list;
    }
    /**
     * 获取本家庭服务器秘钥*
     */
    public static List<DB_HServerKey> GetList(String fserverSN) {
        QueryBuilder qb = db_HServerKeyDao.queryBuilder();
        qb.where(DB_HServerKeyDao.Properties.Hserver_sn.eq(fserverSN));
        List list = qb.list();
        return list;
    }
    /**
     * 获取本家庭服务器秘钥*
     */
    public static List<DB_HServerKey> GetList(String fserverSN, String keyValue) {
        QueryBuilder qb = db_HServerKeyDao.queryBuilder();
        qb.where(DB_HServerKeyDao.Properties.Hserver_sn.eq(fserverSN), DB_HServerKeyDao.Properties.Keyvalue.eq(keyValue));
        List list = qb.list();
        return list;
    }
    /**
     * 获取本家庭服务器秘钥*
     */
    public static DB_HServerKey GetOne(Long key) {
       return db_HServerKeyDao.load(key);
    }
    /**
     * 获取本家庭服务器秘钥*
     */
    public static DB_HServerKey GetOne(String fserverSN, int keyType) {
        QueryBuilder qb = db_HServerKeyDao.queryBuilder();
        qb.where(DB_HServerKeyDao.Properties.Hserver_sn.eq(fserverSN), DB_HServerKeyDao.Properties.Keytype.eq(keyType));
        List list = qb.list();
        if (list != null && list.size() > 0) {
            return (DB_HServerKey) list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 保存*
     */
    public static long Add(DB_HServerKey item) {

        return db_HServerKeyDao.insert(item);
    }
    /**
     * 保存*
     */
    public static long InsertOrReplace(DB_HServerKey item) {

        return db_HServerKeyDao.insertOrReplace(item);
    }
    /**
     * 修改
     */
    public static void Update(DB_HServerKey item) {
          db_HServerKeyDao.update(item);
    }

    /**
     * 删除
     */
    public static void Delete(DB_HServerKey item) {
        db_HServerKeyDao.delete(item);
    }
}

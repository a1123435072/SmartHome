package com.njsyg.smarthomeapp.bll.db_bll;


import com.njsyg.smarthomeapp.dal.DbCore;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import njsyg.greendao.db.dao.DB_DeviceDao;
import njsyg.greendao.db.dao.DB_DeviceRealDataDao;
import njsyg.greendao.db.model.DB_DeviceRealData;

/**
 * Created by user on 2016/8/9.
 */
public class DB_DeviceRealData_BLL {
    private static DB_DeviceRealDataDao db_deviceRealDataDao;

    static {
        if (db_deviceRealDataDao == null) {
            db_deviceRealDataDao = DbCore.getDaoSession().getDB_DeviceRealDataDao();
        }
    }

    /**
     * 获取所有智能设备实时数据
     */
    public static List<DB_DeviceRealData> GetList() {
        return db_deviceRealDataDao.loadAll();

    }
    public static DB_DeviceRealData GetOne(String deviceSN) {
        QueryBuilder qb = db_deviceRealDataDao.queryBuilder();
        qb.where(DB_DeviceRealDataDao.Properties.Device_sn.eq(deviceSN));
        List list = qb.list();
        if(list!=null&&list.size()>0) {
            return (DB_DeviceRealData)list.get(0);
        }else {
            return null;
        }

    }
    /**
     * 获取所有智能设备实时数据
     */
    public static List<DB_DeviceRealData> GetList(String sdeviceSN) {
        QueryBuilder qb = db_deviceRealDataDao.queryBuilder();
        qb.where(DB_DeviceRealDataDao.Properties.Device_sn.eq(sdeviceSN));
        List list = qb.list();
        return list;

    }

    /**
     * 新增*
     */
    public static Long Add(DB_DeviceRealData item) {
        return db_deviceRealDataDao.insert(item);
    }

    /**
     * 新增*
     */
    public static void Add(List<DB_DeviceRealData> item) {
         db_deviceRealDataDao.insertInTx(item);
    }

    /**
     * 更新
     */
    public static void Update(DB_DeviceRealData item)
    {
        db_deviceRealDataDao.update(item);
    }

    /**
     * 添加或更新
     */
    public static long AddOrUpdate(DB_DeviceRealData item) {
        return db_deviceRealDataDao.insertOrReplace(item);
    }

    /**
     * 删除*
     */
    public static void Delete(Long key) {
        db_deviceRealDataDao.deleteByKey(key);
    }
    /**
     * 删除所有*
     */
    public static void Delete() {
        db_deviceRealDataDao.deleteAll();
    }
    /**
     *删除智能设备实时数据*
     */
    public static void Delete(String sdeviceSN)
    {
        List<DB_DeviceRealData> list=GetList(sdeviceSN);
        if(list!=null&&list.size()>0)
        {
            db_deviceRealDataDao.deleteInTx(list);
        }
    }
}

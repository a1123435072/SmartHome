package com.njsyg.smarthomeapp.bll.db_bll;


import com.njsyg.smarthomeapp.dal.DbCore;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
import njsyg.greendao.db.dao.DB_DeviceDao;
import njsyg.greendao.db.model.DB_Device;

/**
 * Created by user on 2016/7/19.
 */
public class DB_Device_BLL {

    private static DB_DeviceDao db_deviceDao;
    static {
        if(db_deviceDao==null)
        {
            db_deviceDao=  DbCore.getDaoSession().getDB_DeviceDao();
        }
    }

    /**
     * 通过家庭服务器sn获取设备*
     */
    public static List<DB_Device> GetList(String hserver_sn) {
        QueryBuilder qb = db_deviceDao.queryBuilder();
        qb.where(DB_DeviceDao.Properties.Hserver_sn.eq(hserver_sn));
        List list = qb.list();
        return list;
    }
    /**
     * 通过家庭服务器sn获取设备*
     */
    public static DB_Device GetOne(String devicesn) {
        QueryBuilder qb = db_deviceDao.queryBuilder();
        qb.where(DB_DeviceDao.Properties.Device_sn.eq(devicesn));
        List list = qb.list();
        if (list != null && list.size() > 0) {
            return (DB_Device) list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 删除*
     */
    public static void Delete(Long key) {
        db_deviceDao.deleteByKey(key);
    }
    /**
    *删除家庭服务器下所有智能设备*
     */
    public static void Delete(String fserverSN)
    {
        List<DB_Device> list=GetList(fserverSN);
       if(list!=null&&list.size()>0)
       {
           db_deviceDao.deleteInTx(list);
       }
    }

    /**
     * 获取*
     */
    public static DB_Device GetOne(Long key) {
    return db_deviceDao.load(key);
    }
    /**
     * 保存*
     */
    public static Long Add(DB_Device item) {
        return db_deviceDao.insert(item);
    }
    /**
     * 保存*
     */
    public static void Update(DB_Device item) {
          db_deviceDao.update(item);
    }

    public static long InsertOrUpdate(DB_Device item)
    {
      return   db_deviceDao.insertOrReplace(item);
    }
}

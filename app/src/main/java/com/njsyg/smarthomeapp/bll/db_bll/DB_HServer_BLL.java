package com.njsyg.smarthomeapp.bll.db_bll;



import com.njsyg.smarthomeapp.dal.DbCore;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
import njsyg.greendao.db.dao.DB_HomeServerDao;
import njsyg.greendao.db.model.DB_HomeServer;

/**
 * Created by user on 2016/7/19.
 */
public class DB_HServer_BLL {
    private static DB_HomeServerDao db_homeServerDao;
    static {
        if(db_homeServerDao==null)
        {
            db_homeServerDao=  DbCore.getDaoSession().getDB_HomeServerDao();
        }
    }

    public static DB_HomeServer GetOne(String fserverSN) {
        QueryBuilder qb = db_homeServerDao.queryBuilder();
        qb.where(DB_HomeServerDao.Properties.Hserver_sn.eq(fserverSN));
        List list = qb.list();
        if (list != null && list.size() > 0) {
            return (DB_HomeServer)list.get(0);
        } else {
            return null;
        }
    }

    public static DB_HomeServer GetOne(Long key) {
        return db_homeServerDao.load(key);
    }

    /**
     * 通过家庭服务器sn获取设备*
     */
    public static List<DB_HomeServer> GetList() {
        return db_homeServerDao.loadAll();
    }
    public void DeleteAll()
    {
        db_homeServerDao.deleteAll();
    }
    public void DeleteOne(DB_HomeServer item) {
        db_homeServerDao.delete(item);
    }

    /**
     * 新增
     */
    public static Long Add(DB_HomeServer item) {
        return db_homeServerDao.insert(item);
    }


    /**
     * 保存
     */
    public static void Update(DB_HomeServer item) {
        db_homeServerDao.update(item);
    }
}

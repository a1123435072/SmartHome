package njsyg.greendao.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import njsyg.greendao.db.model.DB_DeviceOnlineRecord;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DB__DEVICE_ONLINE_RECORD".
*/
public class DB_DeviceOnlineRecordDao extends AbstractDao<DB_DeviceOnlineRecord, Long> {

    public static final String TABLENAME = "DB__DEVICE_ONLINE_RECORD";

    /**
     * Properties of entity DB_DeviceOnlineRecord.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property Device_sn = new Property(1, String.class, "device_sn", false, "DEVICE_SN");
        public final static Property Device_onlineState = new Property(2, Integer.class, "device_onlineState", false, "DEVICE_ONLINE_STATE");
        public final static Property Updatetime = new Property(3, java.util.Date.class, "updatetime", false, "UPDATETIME");
        public final static Property Remark = new Property(4, String.class, "remark", false, "REMARK");
    };


    public DB_DeviceOnlineRecordDao(DaoConfig config) {
        super(config);
    }
    
    public DB_DeviceOnlineRecordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DB__DEVICE_ONLINE_RECORD\" (" + //
                "\"ID\" INTEGER PRIMARY KEY ," + // 0: id
                "\"DEVICE_SN\" TEXT," + // 1: device_sn
                "\"DEVICE_ONLINE_STATE\" INTEGER," + // 2: device_onlineState
                "\"UPDATETIME\" INTEGER," + // 3: updatetime
                "\"REMARK\" TEXT);"); // 4: remark
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DB__DEVICE_ONLINE_RECORD\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DB_DeviceOnlineRecord entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String device_sn = entity.getDevice_sn();
        if (device_sn != null) {
            stmt.bindString(2, device_sn);
        }
 
        Integer device_onlineState = entity.getDevice_onlineState();
        if (device_onlineState != null) {
            stmt.bindLong(3, device_onlineState);
        }
 
        java.util.Date updatetime = entity.getUpdatetime();
        if (updatetime != null) {
            stmt.bindLong(4, updatetime.getTime());
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(5, remark);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DB_DeviceOnlineRecord entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String device_sn = entity.getDevice_sn();
        if (device_sn != null) {
            stmt.bindString(2, device_sn);
        }
 
        Integer device_onlineState = entity.getDevice_onlineState();
        if (device_onlineState != null) {
            stmt.bindLong(3, device_onlineState);
        }
 
        java.util.Date updatetime = entity.getUpdatetime();
        if (updatetime != null) {
            stmt.bindLong(4, updatetime.getTime());
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(5, remark);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DB_DeviceOnlineRecord readEntity(Cursor cursor, int offset) {
        DB_DeviceOnlineRecord entity = new DB_DeviceOnlineRecord( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // device_sn
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // device_onlineState
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)), // updatetime
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // remark
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DB_DeviceOnlineRecord entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDevice_sn(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDevice_onlineState(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setUpdatetime(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
        entity.setRemark(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DB_DeviceOnlineRecord entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DB_DeviceOnlineRecord entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}

package njsyg.greendao.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import njsyg.greendao.db.model.DB_DeviceRealData;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DB__DEVICE_REAL_DATA".
*/
public class DB_DeviceRealDataDao extends AbstractDao<DB_DeviceRealData, Long> {

    public static final String TABLENAME = "DB__DEVICE_REAL_DATA";

    /**
     * Properties of entity DB_DeviceRealData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property Device_sn = new Property(1, String.class, "device_sn", false, "DEVICE_SN");
        public final static Property Device_state = new Property(2, Integer.class, "device_state", false, "DEVICE_STATE");
        public final static Property Device_U = new Property(3, Float.class, "device_U", false, "DEVICE__U");
        public final static Property Device_I = new Property(4, Float.class, "device_I", false, "DEVICE__I");
        public final static Property Device_P = new Property(5, Float.class, "device_P", false, "DEVICE__P");
        public final static Property Device_electricity = new Property(6, Float.class, "device_electricity", false, "DEVICE_ELECTRICITY");
        public final static Property Updatetime = new Property(7, java.util.Date.class, "updatetime", false, "UPDATETIME");
        public final static Property Remark = new Property(8, String.class, "remark", false, "REMARK");
    };


    public DB_DeviceRealDataDao(DaoConfig config) {
        super(config);
    }
    
    public DB_DeviceRealDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DB__DEVICE_REAL_DATA\" (" + //
                "\"ID\" INTEGER PRIMARY KEY ," + // 0: id
                "\"DEVICE_SN\" TEXT," + // 1: device_sn
                "\"DEVICE_STATE\" INTEGER," + // 2: device_state
                "\"DEVICE__U\" REAL," + // 3: device_U
                "\"DEVICE__I\" REAL," + // 4: device_I
                "\"DEVICE__P\" REAL," + // 5: device_P
                "\"DEVICE_ELECTRICITY\" REAL," + // 6: device_electricity
                "\"UPDATETIME\" INTEGER," + // 7: updatetime
                "\"REMARK\" TEXT);"); // 8: remark
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DB__DEVICE_REAL_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DB_DeviceRealData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String device_sn = entity.getDevice_sn();
        if (device_sn != null) {
            stmt.bindString(2, device_sn);
        }
 
        Integer device_state = entity.getDevice_state();
        if (device_state != null) {
            stmt.bindLong(3, device_state);
        }
 
        Float device_U = entity.getDevice_U();
        if (device_U != null) {
            stmt.bindDouble(4, device_U);
        }
 
        Float device_I = entity.getDevice_I();
        if (device_I != null) {
            stmt.bindDouble(5, device_I);
        }
 
        Float device_P = entity.getDevice_P();
        if (device_P != null) {
            stmt.bindDouble(6, device_P);
        }
 
        Float device_electricity = entity.getDevice_electricity();
        if (device_electricity != null) {
            stmt.bindDouble(7, device_electricity);
        }
 
        java.util.Date updatetime = entity.getUpdatetime();
        if (updatetime != null) {
            stmt.bindLong(8, updatetime.getTime());
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(9, remark);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DB_DeviceRealData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String device_sn = entity.getDevice_sn();
        if (device_sn != null) {
            stmt.bindString(2, device_sn);
        }
 
        Integer device_state = entity.getDevice_state();
        if (device_state != null) {
            stmt.bindLong(3, device_state);
        }
 
        Float device_U = entity.getDevice_U();
        if (device_U != null) {
            stmt.bindDouble(4, device_U);
        }
 
        Float device_I = entity.getDevice_I();
        if (device_I != null) {
            stmt.bindDouble(5, device_I);
        }
 
        Float device_P = entity.getDevice_P();
        if (device_P != null) {
            stmt.bindDouble(6, device_P);
        }
 
        Float device_electricity = entity.getDevice_electricity();
        if (device_electricity != null) {
            stmt.bindDouble(7, device_electricity);
        }
 
        java.util.Date updatetime = entity.getUpdatetime();
        if (updatetime != null) {
            stmt.bindLong(8, updatetime.getTime());
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(9, remark);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DB_DeviceRealData readEntity(Cursor cursor, int offset) {
        DB_DeviceRealData entity = new DB_DeviceRealData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // device_sn
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // device_state
            cursor.isNull(offset + 3) ? null : cursor.getFloat(offset + 3), // device_U
            cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4), // device_I
            cursor.isNull(offset + 5) ? null : cursor.getFloat(offset + 5), // device_P
            cursor.isNull(offset + 6) ? null : cursor.getFloat(offset + 6), // device_electricity
            cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)), // updatetime
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // remark
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DB_DeviceRealData entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDevice_sn(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDevice_state(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setDevice_U(cursor.isNull(offset + 3) ? null : cursor.getFloat(offset + 3));
        entity.setDevice_I(cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4));
        entity.setDevice_P(cursor.isNull(offset + 5) ? null : cursor.getFloat(offset + 5));
        entity.setDevice_electricity(cursor.isNull(offset + 6) ? null : cursor.getFloat(offset + 6));
        entity.setUpdatetime(cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)));
        entity.setRemark(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DB_DeviceRealData entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DB_DeviceRealData entity) {
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

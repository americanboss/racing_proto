package jp.co.equinestudio.racing.dao.green;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import jp.co.equinestudio.racing.dao.green.FavoriteHorse;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table FAVORITE_HORSE.
*/
public class FavoriteHorseDao extends AbstractDao<FavoriteHorse, Long> {

    public static final String TABLENAME = "FAVORITE_HORSE";

    /**
     * Properties of entity FavoriteHorse.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Horse_code = new Property(1, String.class, "horse_code", false, "HORSE_CODE");
        public final static Property Horse_name = new Property(2, String.class, "horse_name", false, "HORSE_NAME");
        public final static Property Group = new Property(3, Integer.class, "group", false, "GROUP");
        public final static Property Reg_timestamp = new Property(4, Long.class, "reg_timestamp", false, "REG_TIMESTAMP");
    };


    public FavoriteHorseDao(DaoConfig config) {
        super(config);
    }
    
    public FavoriteHorseDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'FAVORITE_HORSE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'HORSE_CODE' TEXT," + // 1: horse_code
                "'HORSE_NAME' TEXT," + // 2: horse_name
                "'GROUP' INTEGER," + // 3: group
                "'REG_TIMESTAMP' INTEGER);"); // 4: reg_timestamp
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'FAVORITE_HORSE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, FavoriteHorse entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String horse_code = entity.getHorse_code();
        if (horse_code != null) {
            stmt.bindString(2, horse_code);
        }
 
        String horse_name = entity.getHorse_name();
        if (horse_name != null) {
            stmt.bindString(3, horse_name);
        }
 
        Integer group = entity.getGroup();
        if (group != null) {
            stmt.bindLong(4, group);
        }
 
        Long reg_timestamp = entity.getReg_timestamp();
        if (reg_timestamp != null) {
            stmt.bindLong(5, reg_timestamp);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public FavoriteHorse readEntity(Cursor cursor, int offset) {
        FavoriteHorse entity = new FavoriteHorse( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // horse_code
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // horse_name
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // group
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4) // reg_timestamp
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, FavoriteHorse entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setHorse_code(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setHorse_name(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setGroup(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setReg_timestamp(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(FavoriteHorse entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(FavoriteHorse entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}

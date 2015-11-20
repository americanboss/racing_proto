package jp.co.equinestudio.racing.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import jp.co.equinestudio.racing.dao.green.DaoMaster;
import jp.co.equinestudio.racing.dao.green.DaoSession;

/**
 *
 */
public class DbHelper extends DaoMaster.OpenHelper {

    private static final String DB_NAME = "company";
    private static DbHelper INSTANCE;
    private static DaoMaster DAO_MASTER;
    private static DaoSession DAO_SESSION;

    public DbHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory);

        DAO_MASTER = new DaoMaster(this.getWritableDatabase());
        DAO_SESSION = DAO_MASTER.newSession();
    }

    public static DbHelper getInstance(Context context) {
        if(INSTANCE == null)
            INSTANCE = new DbHelper(context, null);

        return INSTANCE;
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        super.onCreate(db);
    }

    public static DaoMaster master() {
        return DAO_MASTER;
    }

    public static DaoSession session() {
        return DAO_SESSION;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

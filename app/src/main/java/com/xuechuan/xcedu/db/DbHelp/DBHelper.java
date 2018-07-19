package com.xuechuan.xcedu.db.DbHelp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xuechuan.xcedu.db.DaoMaster;
import com.xuechuan.xcedu.db.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.db.DbHelp
 * @Description: db帮助类
 * @author: L-BackPacker
 * @date: 2018/5/9 11:06
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DBHelper {
    private static final String TAG = "DBHelper";
    private static MyOpenHelper mHelper;
    private static SQLiteDatabase db;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    /**
     * 设置greenDao,在application的onCreate方法初始化
     */
    public static void initDb(Context context) {

        if (mHelper != null && mDaoSession != null) {
            return;
        }
        /**context 上下文
         * test-db 数据库名称
         */
        try {
            DatabaseContext databaseContext = new DatabaseContext(context);
            mHelper = new MyOpenHelper(databaseContext, "userinfom.db", null);
            db = mHelper.getWritableDatabase();
            mDaoMaster = new DaoMaster(db);
            mDaoSession = mDaoMaster.newSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    /**
     * 默认DaoMaster.OpenHelper，数据库升级删除之前所有的数据
     * 自定义MyOpenHelper，实际开发中，数据库升级要保存原先的数据
     */
    private static class MyOpenHelper extends DaoMaster.OpenHelper {

        public MyOpenHelper(Context context, String name) {
            super(context, name);
        }

        public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onCreate(Database db) {
            super.onCreate(db);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            super.onUpgrade(db, oldVersion, newVersion);
            Log.e(TAG, "onUpgrade: 更新啦");
//            MigrationHelper.getInstance().migrate(db,userinfom.class, SchoolDao.class);
        }
    }

}

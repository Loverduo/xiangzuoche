package com.ruixiangtong.xzc.db.dbBusiness;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.ruixiangtong.xzc.db.DbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xrj on 2015/7/8.
 */
public class BaseBusiness {
    /**
     * 对象锁
     */
    public static final Object lock = new Object();


    protected static SQLiteDatabase db;


    protected DbHelper dbHelper;

    String systemDBName = "sqlite_master";

    /**
     * 数据库名称
     */
    private static final String dbName = "superVIPMerchant.db";

    /**
     * 数据库版本
     * 1 2015-07-16 14:39:59 初次创建
     */
    private static final int dbVersion = 1;


    public BaseBusiness() {

    }

    public BaseBusiness(Context context) {
        dbHelper = new DbHelper(context, dbName, null, dbVersion);
        synchronized (lock) {
            openDatabase(context);
        }
    }

    /**
     * 获取数据库对象
     *
     * @param context
     * @return
     */
    protected SQLiteDatabase openDatabase(Context context) {
        if (context == null) {
            return null;
        }
        if (db == null || !db.isOpen()) {
            try {
                db = dbHelper.getWritableDatabase();
            } catch (Exception e) {
                if (e != null) {
                    e.printStackTrace();
                }
            }
        }
        return db;
    }

    /**
     * 判断指定表是否存在
     *
     * @param tableName 表名
     * @return
     */
    public boolean tabbleIsExist(String tableName) {
        boolean result = false;
        if (TextUtils.isEmpty(tableName)) {
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from " + systemDBName + " where type ='table' and name ='" + tableName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                    return result;
                }
            }

        } catch (Exception e) {
        }
        return result;
    }

    public long insert(SQLiteDatabase db, String tableName, ContentValues contentValues) {
        if (db == null || TextUtils.isEmpty(tableName)) {
            return -1;
        }
        try {
            return db.insert(tableName, null, contentValues);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * 清除表数据
     *
     * @param db SQLiteDatabase 对象
     * @return int
     */
    public int clearTable(SQLiteDatabase db, String table) {
        int result = -1;
        try {
            if (db != null) {
                result = db.delete(table, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private int dropTable(String tableLike){
        int result = 0;
        Cursor cursor = db.query("sqlite_master", new String[]{"name"}, "name like?", new String []{tableLike+"%"}, null, null, null);
        if(cursor == null || cursor.getCount() <=0 ){
            return -2;
        }
        List<String> chatList = new ArrayList<>();
        while (cursor.moveToNext()){
            chatList.add(cursor.getString(0));
        }
        if(chatList.size() <= 0){
            return -2;
        }
        try {
            for (int i = 0; i < chatList.size(); i++) {
                db.execSQL("drop table " + chatList.get(i));
            }
            result = 1;
        } catch (Exception e) {
            e.printStackTrace();
            result = -1;
        }
        return result;
    }
}
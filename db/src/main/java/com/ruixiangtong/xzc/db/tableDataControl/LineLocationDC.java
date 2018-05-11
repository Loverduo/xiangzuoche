package com.ruixiangtong.xzc.db.tableDataControl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ruixiangtong.xzc.db.DBConstant;


/**
 * Created by XURJ on 2016/5/28.
 */
public class LineLocationDC {
    public static final String TABLE_NAME = "t_line_location";
    /**
     * 自增id
     */
    public static final String ID = "_id";

    /**
     * 线路名称
     */
    public static final String LINE_ID = "lineId";

    /**
     * 站点名称
     */
    public static final String LOCATION_NAME = "locationName";

    /**
     * 站点顺序
     */
    public static final String LOCATION_ORDER = "locationOrder";

    /**
     * 纬度
     */
    public static final String LATITUDE = "latitude";

    /**
     * 经度
     */
    public static final String LONGITUDE = "longitude";

    /**
     * 预计从出发点到此所需要的时间
     */
    public static final String ESTIMATE_TIME = "estimateTime";

    /**
     * 线路类型 1 上行线路 2 下行线路
     */
    public static final String LINE_TYPE = "lineType";

    /**
     * 位置类型 0 无站点
     * 1 真实站点
     */
    public static final String LOCATION_TYPE = "locationType";

    /**
     * 距离前一个点的距离
     */
    public static final String DISTANCE_PRIOR_LOCATION = "distancePriorLocation";

    private String tableName = "";

    public LineLocationDC() {

    }

    public void createTable(SQLiteDatabase db, String lineNum) {

        tableName = TABLE_NAME + "_" + lineNum;

        db.execSQL(DBConstant.CREATETABLE + tableName + " ("
                + ID + DBConstant.PRIVATEKEY/* 主键自增 */
                + LINE_ID + DBConstant.INTEGERTYPE/*线路名称*/
                + LOCATION_NAME + DBConstant.TEXTTYPE/*站点名称*/
                + LOCATION_ORDER + DBConstant.INTEGERTYPE /*站点顺序*/
                + LATITUDE + DBConstant.TEXTTYPE/*纬度*/
                + LONGITUDE + DBConstant.TEXTTYPE/* 经度 */
                + ESTIMATE_TIME + DBConstant.INTEGERTYPE/*预计从出发点到此所需要的时间*/
                + LINE_TYPE + DBConstant.INTEGERTYPE/*线路类型*/
                + LOCATION_TYPE + DBConstant.INTEGERTYPE/*位置类型*/
                + DISTANCE_PRIOR_LOCATION + DBConstant.INTEGERTYPE_END/*距离前一个点的距离*/
                + ");");
    }

    public long insertLocation(SQLiteDatabase db, ContentValues contentValues) {
        if (contentValues == null || db == null) {
            return -1;
        }
        long resCode = 0;
        try {
            resCode = db.insert(tableName, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return resCode;
    }

    public int delete(SQLiteDatabase db, int lineType) {
        if (db == null) {
            return -1;
        }
        try {
            return db.delete(tableName,
                    LINE_TYPE + " = ?",
                    new String[]{String.valueOf(lineType)});
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public Cursor getLocationList(SQLiteDatabase db, int lineType, int locationType) {
        if (db == null) {
            return null;
        }
        String sql = "select * from " + tableName + " where " +
                LINE_TYPE + " = '" + lineType + "' and " + LOCATION_TYPE + " = '" + locationType + "' order by " + LOCATION_ORDER;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }

        return cursor;
    }
}

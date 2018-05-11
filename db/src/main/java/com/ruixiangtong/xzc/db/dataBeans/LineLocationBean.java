package com.ruixiangtong.xzc.db.dataBeans;

import android.content.ContentValues;
import android.database.Cursor;

import com.ruixiangtong.xzc.db.tableDataControl.LineLocationDC;

/**
 * Created by xurj on 2017/7/11.
 */
public class LineLocationBean {
    /**
     * 线路途经点
     */
    public static final int LINELOCATION_TYPE_STATION = 0;

    /**
     * 线路站点
     */
    public static final int LINELOCATION_TYPE_STEP = 1;

    /**
     * 线路id
     */
    private int lineId;

    /**
     * 站点名称
     */
    private String locationName;

    /**
     * 站点顺序
     */
    private int locationOrder;

    /**
     * 纬度
     */
    private double latitude;

    /**
     * 经度
     */
    private double longitude;

    /**
     * 预计从出发点到此所需要的时间
     */
    private long estimateTime;

    /**
     * 线路类型 1 上行线路 2 下行线路
     */
    private int lineType;

    /**
     * 位置类型
     * 0 无站点
     * 1 真实站点
     */
    private int locationType;

    /**
     * 距离前一个点的距离
     */
    private double distancePriorLocation;

    public LineLocationBean() {

    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getLocationOrder() {
        return locationOrder;
    }

    public void setLocationOrder(int locationOrder) {
        this.locationOrder = locationOrder;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(long estimateTime) {
        this.estimateTime = estimateTime;
    }

    public int getLineType() {
        return lineType;
    }

    public void setLineType(int lineType) {
        this.lineType = lineType;
    }

    public int getLocationType() {
        return locationType;
    }

    public void setLocationType(int locationType) {
        this.locationType = locationType;
    }

    public double getDistancePriorLocation() {
        return distancePriorLocation;
    }

    public void setDistancePriorLocation(double distancePriorLocation) {
        this.distancePriorLocation = distancePriorLocation;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LineLocationDC.LINE_ID, lineId);
        contentValues.put(LineLocationDC.LOCATION_NAME, locationName);
        contentValues.put(LineLocationDC.LOCATION_ORDER, locationOrder);
        contentValues.put(LineLocationDC.LATITUDE, latitude + "");
        contentValues.put(LineLocationDC.LONGITUDE, longitude + "");
        contentValues.put(LineLocationDC.ESTIMATE_TIME, estimateTime);
        contentValues.put(LineLocationDC.LINE_TYPE, lineType + "");
        contentValues.put(LineLocationDC.LOCATION_TYPE, locationType + "");
        contentValues.put(LineLocationDC.DISTANCE_PRIOR_LOCATION, distancePriorLocation);
        return contentValues;
    }

    public LineLocationBean(Cursor cursor) {
        super();
        if (cursor != null) {
            lineId = cursor.getInt(cursor.getColumnIndex(LineLocationDC.LINE_ID));
            locationName = cursor.getString(cursor.getColumnIndex(LineLocationDC.LOCATION_NAME));
            locationOrder = cursor.getInt(cursor.getColumnIndex(LineLocationDC.LOCATION_ORDER));
            latitude = cursor.getDouble(cursor.getColumnIndex(LineLocationDC.LATITUDE));
            longitude = cursor.getDouble(cursor.getColumnIndex(LineLocationDC.LONGITUDE));
            estimateTime = cursor.getLong(cursor.getColumnIndex(LineLocationDC.ESTIMATE_TIME));
            lineType = cursor.getInt(cursor.getColumnIndex(LineLocationDC.LINE_TYPE));
            locationType = cursor.getInt(cursor.getColumnIndex(LineLocationDC.LOCATION_TYPE));
            distancePriorLocation = cursor.getDouble(cursor.getColumnIndex(LineLocationDC.DISTANCE_PRIOR_LOCATION));
        }
    }
}

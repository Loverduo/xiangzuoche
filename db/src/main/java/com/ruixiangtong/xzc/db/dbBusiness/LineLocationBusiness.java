package com.ruixiangtong.xzc.db.dbBusiness;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.ruixiangtong.common.bean.GPSPoint;
import com.ruixiangtong.xzc.db.dataBeans.LineLocationBean;
import com.ruixiangtong.xzc.db.tableDataControl.LineLocationDC;

import java.util.ArrayList;

/**
 * Created by XURJ on 2016/5/28.
 */
public class LineLocationBusiness extends BaseBusiness {
    private LineLocationDC dc;

    public LineLocationBusiness(Context context, String lineNum) {
        super(context);
        dc = new LineLocationDC();
        Log.e("Test", "LineLoactionBusiness() lineNum : " + lineNum);
        if (!tabbleIsExist(LineLocationDC.TABLE_NAME + "_" + lineNum)) {
            Log.e("Test", "" + lineNum + " tabbleIsExist : false");
            dc.createTable(db, lineNum);
        }

    }

    public long insertLocation(LineLocationBean lineLocationBean) {
        long result = -1;

        synchronized (lock) {
            result = dc.insertLocation(db, lineLocationBean.getContentValues());
            return result;
        }
    }

    public int clearLocation(int lineType) {
        synchronized (lock) {
            return dc.delete(db, lineType);
        }
    }

    public ArrayList<GPSPoint> getLoactionList(int lineType, int locationType) {
        ArrayList<GPSPoint> locationList = null;
        Cursor cursor = null;
        synchronized (lock) {
            cursor = dc.getLocationList(db, lineType, locationType);
        }
        if (cursor != null) {
            locationList = new ArrayList<>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                GPSPoint point = new GPSPoint();
                point.setLatitude(cursor.getFloat(cursor.getColumnIndex(LineLocationDC.LATITUDE)));
                point.setLongitude(cursor.getFloat(cursor.getColumnIndex(LineLocationDC.LONGITUDE)));
                if (!locationList.contains(point)) {
                    locationList.add(point);
                }
                cursor.moveToNext();
            }
        }

        return locationList;
    }
}

package com.ruixiangtong.common.bean;

/**
 * Created by xrj on 2016/5/19.
 */
public class GPSPoint {
    float latitude;
    float longitude;

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if(((GPSPoint)o).latitude== this.latitude && ((GPSPoint)o).longitude == this.longitude){
            return true;
        }

        return false;
    }
}

package com.ruixiangtong.xiangzuoche;

import android.app.Application;

import com.ruixiangtong.xzc.http.HttpApplication;
import com.ruixiangtong.xzc.map.MapApplication;


/**
 * Created by xurj on 2016/7/13.
 */
public class XzcApplication extends Application {
    private MapApplication mapApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        HttpApplication.context = this;
        mapApplication = new MapApplication(this);
    }
}

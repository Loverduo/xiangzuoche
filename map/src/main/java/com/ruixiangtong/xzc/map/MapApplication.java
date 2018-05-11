package com.ruixiangtong.xzc.map;

import android.app.Service;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Vibrator;
import android.util.Log;

import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.ruixiangtong.xzc.map.service.LocationService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xurj on 2016/7/13.
 */
public class MapApplication {
    public static LocationService locationService;

    public Vibrator mVibrator;

    public MapApplication(Context context){
       // getDbFile(context);

        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(context);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(context);
        LocationClientOption mOption = locationService.getDefaultLocationClientOption();
        locationService.setLocationOption(mOption);

        mVibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
    }

    public static void startGetLocation(){
        locationService.start();
    }

    public static void stopGetLoaction(){
        locationService.stop();
    }
    private void getDbFile(Context context) {
        String dbfile = "/data/data/com.ruixiangtong.xzc/databases/superVIPMerchant.db";
        String dir = "/data/data/com.ruixiangtong.xzc/databases";
        try {
            File file = new File(dbfile);
            if (!(file.exists())) { //判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                File dirs = new File(dir);
                dirs.mkdirs();
                try {
                    //得到资源
                    AssetManager am = context.getAssets();
                    //得到数据库的输入流
                    InputStream is = am.open("superVIPMerchant.db");
                    Log.i("test", is + "");
                    //用输出流写到SDcard上面
                    FileOutputStream fos = new FileOutputStream(dbfile);
                    Log.i("test", "fos=" + fos);
                    Log.i("test", "jhPath=" + dbfile);
                    //创建byte数组  用于1KB写一次
                    byte[] buffer = new byte[1024];
                    int count = 0;
                    while ((count = is.read(buffer)) > 0) {
                        Log.i("test", "得到");
                        fos.write(buffer, 0, count);
                    }
                    //最后关闭就可以了
                    fos.flush();
                    fos.close();
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }  catch (Exception e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
    }
}

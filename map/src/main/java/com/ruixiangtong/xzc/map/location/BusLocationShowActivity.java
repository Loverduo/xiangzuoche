package com.ruixiangtong.xzc.map.location;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.BusLineOverlay;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.ruixiangtong.common.bean.GPSPoint;
import com.ruixiangtong.xzc.db.dataBeans.LineLocationBean;
import com.ruixiangtong.xzc.db.dbBusiness.LineLocationBusiness;
import com.ruixiangtong.xzc.map.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 此demo用来展示如何进行公交线路详情检索，并使用RouteOverlay在地图上绘制 同时展示如何浏览路线节点并弹出泡泡
 */
public class BusLocationShowActivity extends Activity implements
        OnGetPoiSearchResultListener, OnGetBusLineSearchResultListener,
        BaiduMap.OnMapClickListener {
    private EditText editSearchKey;
    private Button mBtnPre = null; // 上一个节点
    private Button mBtnNext = null; // 下一个节点
    private int nodeIndex = -2; // 节点索引,供浏览节点时使用
    private BusLineResult route = null; // 保存驾车/步行路线数据的变量，供浏览节点时使用
    private List<String> busLineIDList = null;
    private int busLineIndex = 0;
    // 搜索相关
    private PoiSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用
    private BusLineSearch mBusLineSearch = null;
    private MapView mapView;
    private BaiduMap mBaiduMap = null;
    BusLineOverlay overlay; // 公交路线绘制对象
//    BusLineOverlay overlay1; // 公交路线绘制对象

    private ArrayList<LatLng> points;
    private int count = 0;

    /**
     * 1上行 2下行
     */
    private int lineType = 1;

    LineLocationBusiness lineLocationBusiness;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busline);
        CharSequence titleLable = "公交线路查询功能";
        setTitle(titleLable);
        mBtnPre = (Button) findViewById(R.id.pre);
        mBtnNext = (Button) findViewById(R.id.next);
        mBtnPre.setVisibility(View.INVISIBLE);
        mBtnNext.setVisibility(View.INVISIBLE);
        mapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mapView.getMap();
        mBaiduMap.setOnMapClickListener(this);
        mSearch = PoiSearch.newInstance();
        mSearch.setOnGetPoiSearchResultListener(this);
        mBusLineSearch = BusLineSearch.newInstance();
        mBusLineSearch.setOnGetBusLineSearchResultListener(this);
        busLineIDList = new ArrayList<String>();
        overlay = new BusLineOverlay(mBaiduMap);
//        overlay1 = new BusLineOverlay(mBaiduMap);
        mBaiduMap.setOnMarkerClickListener(overlay);

        List<OverlayOptions> overlayOptions = overlay.getOverlayOptions();

        //设定中心点坐标
        LatLng cenpt = new LatLng(34.82222689522203, 114.82011670562784);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(15)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化


        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);

        //显示实时路况
        mBaiduMap.setTrafficEnabled(true);
        mBaiduMap.clear();

        Log.e("Test", "onCrate()");
        lineLocationBusiness = new LineLocationBusiness(this, "101");
        ArrayList<GPSPoint> locationList = lineLocationBusiness.getLoactionList(1, 0);
        if (locationList != null) {
            for (int i = 0; i < locationList.size(); i++) {
                Log.e("Test", "i = " + i + " , la : " + locationList.get(i).getLatitude() + " , ln : " + locationList.get(i).getLongitude());
            }
        }
    }

    /**
     * 发起检索
     *
     * @param v
     */
    public void searchButtonProcess(View v) {
        busLineIDList.clear();
        busLineIndex = 0;
        mBtnPre.setVisibility(View.INVISIBLE);
        mBtnNext.setVisibility(View.INVISIBLE);
        EditText editCity = (EditText) findViewById(R.id.city);
        editSearchKey = (EditText) findViewById(R.id.searchkey);
        // 发起poi检索，从得到所有poi中找到公交线路类型的poi，再使用该poi的uid进行公交详情搜索
//        mSearch.searchInCity((new PoiCitySearchOption()).city(
//               "开封")
//                .keyword("101"));
        mSearch.searchInCity((new PoiCitySearchOption()).city(
                editCity.getText().toString())
                .keyword(editSearchKey.getText().toString()));
    }

    public void searchNextBusLine(View v) {
        if (busLineIndex >= busLineIDList.size()) {
            busLineIndex = 0;
        }
        if (busLineIndex >= 0 && busLineIndex < busLineIDList.size()
                && busLineIDList.size() > 0) {
            mBusLineSearch.searchBusLine((new BusLineSearchOption()
                    .city(((EditText) findViewById(R.id.city)).getText()
                            .toString()).uid(busLineIDList.get(busLineIndex))));

            busLineIndex++;
        }

    }

    /**
     * 节点浏览示例
     *
     * @param v
     */
    public void nodeClick(View v) {

        if (nodeIndex < -1 || route == null
                || nodeIndex >= route.getStations().size()) {
            return;
        }
        TextView popupText = new TextView(this);
        popupText.setBackgroundResource(R.drawable.popup);
        popupText.setTextColor(0xff000000);
        // 上一个节点
        if (mBtnPre.equals(v) && nodeIndex > 0) {
            // 索引减
            nodeIndex--;
        }
        // 下一个节点
        if (mBtnNext.equals(v) && nodeIndex < (route.getStations().size() - 1)) {
            // 索引加
            nodeIndex++;
        }
        if (nodeIndex >= 0) {
            // 移动到指定索引的坐标
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(route
                    .getStations().get(nodeIndex).getLocation()));
            // 弹出泡泡
            popupText.setText(route.getStations().get(nodeIndex).getTitle());
            mBaiduMap.showInfoWindow(new InfoWindow(popupText, route.getStations()
                    .get(nodeIndex).getLocation(), 10));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mSearch.destroy();
        mBusLineSearch.destroy();
        super.onDestroy();
    }

    @Override
    public void onGetBusLineResult(BusLineResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(BusLocationShowActivity.this, "抱歉，未找到结果",
                    Toast.LENGTH_LONG).show();
            return;
        }
        // mBaiduMap.clear();
        lineLocationBusiness = new LineLocationBusiness(this, editSearchKey.getText().toString());
        List<LatLng> busSteps = result.getSteps().get(0).getWayPoints();
        List<BusLineResult.BusStation> busStations = result.getStations();

//        LineLocationBean lineLocationBean;
//        for(int i= 0 ; i < busSteps.size();i++){
//            lineLocationBean = new LineLocationBean();
//            lineLocationBean.setLatitude(busSteps.get(i).latitude);
//            lineLocationBean.setLongitude(busSteps.get(i).longitude);
//            lineLocationBean.setLocationOrder(i);
//            lineLocationBean.setLocationName("");
//            lineLocationBean.setLocationType(LineLocationBean.LINELOCATION_TYPE_STATION);
//            for(int j = 0 ; j < busStations.size();j++){
//                if(busStations.get(j).getLocation().latitude == busSteps.get(i).latitude
//                        && busStations.get(j).getLocation().longitude == busSteps.get(i).longitude){
//                    lineLocationBean.setLocationName(busStations.get(j).getTitle());
//                    lineLocationBean.setLocationType(LineLocationBean.LINELOCATION_TYPE_STEP);
//                    break;
//                }
//            }
//            lineLocationBusiness.insertLocation(lineLocationBean);
//        }


        LineLocationBean lineLocationBean = new LineLocationBean();
        //路径途经点
        for (int i = 0; i < busSteps.size(); i++) {
            lineLocationBean.setLatitude(busSteps.get(i).latitude);
            lineLocationBean.setLongitude(busSteps.get(i).longitude);
            lineLocationBean.setLocationOrder(i);
            lineLocationBean.setLocationName("");
            lineLocationBean.setLineType(lineType);
            lineLocationBean.setLocationType(LineLocationBean.LINELOCATION_TYPE_STATION);

            //lineLocationBusiness.insertLocation(lineLocationBean);

            Log.e("Test", "points.add(new LatLng(" + busSteps.get(i).latitude + "," + busSteps.get(i).longitude + "));");
        }

        //公交停靠站
        for (int i = 100; i < busStations.size()+100; i++) {
            lineLocationBean.setLatitude(busStations.get(i-100).getLocation().latitude);
            lineLocationBean.setLongitude(busStations.get(i-100).getLocation().longitude);
            lineLocationBean.setLocationOrder(i);
            lineLocationBean.setLineType(lineType);
            lineLocationBean.setLocationName(busStations.get(i-100).getTitle());
            lineLocationBean.setLocationType(LineLocationBean.LINELOCATION_TYPE_STEP);
//            lineLocationBusiness.insertLocation(lineLocationBean);

//            Log.e("Test", "points.add(new LatLng(" + busSteps.get(i).latitude + "," + busSteps.get(i).longitude + "));");
            Log.e("Test", "LatLng llCircle"+i+" = new LatLng(" + busStations.get(i-100).getLocation().latitude + "," + busStations.get(i-100).getLocation().longitude + ");\n" +
                    "        OverlayOptions ooCircle"+i+" = new CircleOptions().fillColor(0x000000FF)\n" +
                    "                .center(llCircle"+i+").stroke(new Stroke(5, 0x80000000))\n" +
                    "                .radius(14);\n" +
                    "        mBaiduMap.addOverlay(ooCircle"+i+");");
        }

        route = result;
        nodeIndex = -1;
        if (count == 0) {
            overlay.removeFromMap();
            overlay.setData(result);
            overlay.addToMap();
            overlay.zoomToSpan();
            count++;
        } else if (count == 1) {
//            overlay1.removeFromMap();
//            overlay1.setData(result);
//            overlay1.addToMap();
//            overlay1.zoomToSpan();
//            count++;
        }


//        List<BitmapDescriptor> customList = new ArrayList<BitmapDescriptor>();
//        BitmapDescriptor custom1 = BitmapDescriptorFactory
//                .fromResource(R.color.red);
//        BitmapDescriptor custom2 = BitmapDescriptorFactory
//                .fromResource(R.color.red);
//        customList.add(custom1);
//        customList.add(custom2);
//        //添加到地�?
//        points = new ArrayList<LatLng>();
//        points.add(pointMiddle);
//        points.add(pointEnd);
//        overlay = new PolylineOptions().width(10).color(0xAAFF0000).
//                points(points).customTextureList(customList).textureIndex(index);
//        OverlayOptions overlayOptions = new OverlayOptions();
//        mBaiduMap.addOverlay(overlay);
//        mapView.refreshDrawableState();


        mBtnPre.setVisibility(View.VISIBLE);
        mBtnNext.setVisibility(View.VISIBLE);
        Toast.makeText(BusLocationShowActivity.this, result.getBusLineName(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetPoiResult(PoiResult result) {

        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(BusLocationShowActivity.this, "抱歉，未找到结果",
                    Toast.LENGTH_LONG).show();
            return;
        }
        // 遍历所有poi，找到类型为公交线路的poi
        busLineIDList.clear();
        for (PoiInfo poi : result.getAllPoi()) {
            if (poi.type == PoiInfo.POITYPE.BUS_LINE
                    || poi.type == PoiInfo.POITYPE.SUBWAY_LINE) {
                busLineIDList.add(poi.uid);
            }
        }
        searchNextBusLine(null);
        route = null;
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    @Override
    public void onMapClick(LatLng point) {
        mBaiduMap.hideInfoWindow();
    }

    @Override
    public boolean onMapPoiClick(MapPoi poi) {
        return false;
    }


}

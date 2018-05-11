package com.ruixiangtong.xzc.map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示公交车当前所运行路线和所在位置
 *
 * @author Xurj
 */
public class BusCurLocationActivity extends Activity {
    // 百度地图控件
    private MapView mMapView = null;

    // 百度地图对象
    private BaiduMap mBaiduMap;

    // 纹理折线，点击时获取折线上点数及width
    Polyline mTexturePolyline;

    private int mCurrentDirection = 0;
    boolean isFollowLocation = true; // 是否跟随当前位置
    private MyLocationData locData;

    private LocationReceiver locationReceiver;

    private Button followBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bus_cur_location);
        mMapView = (MapView) findViewById(R.id.bmapview);

        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(18.0f);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapStatus(msu);

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        //路况信息
        mBaiduMap.setTrafficEnabled(true);

        //开启定位
        MapApplication.startGetLocation();

        locationReceiver = new LocationReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocationReceiver.ACTION_LOCATION);
        registerReceiver(locationReceiver, intentFilter);

        mBaiduMap.setOnMapDoubleClickListener(new BaiduMap.OnMapDoubleClickListener() {
            @Override
            public void onMapDoubleClick(LatLng latLng) {
                Log.e("Test", "onMapDoubleClick");
                isFollowLocation = false;
            }
        });

        mBaiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.e("Test", "onTouch");
                    isFollowLocation = false;
                }
            }
        });

        followBtn = (Button) findViewById(R.id.followLocation);
        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFollowLocation = true;
            }
        });

        addCustomElementsDemo();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MapApplication.stopGetLoaction();
        mMapView.onDestroy();
    }

    /**
     * 定位SDK监听函数, 需实现BDLocationListener里的方法
     */
    public class LocationReceiver extends BroadcastReceiver {
        public static final String ACTION_LOCATION = "com.ruixiangtong.xiangzuoche.ACTION_LOCATION";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }

            String action = intent.getAction();
            if (action != null && action.equals(ACTION_LOCATION)) {
                LatLng latLng = intent.getParcelableExtra("LatLng");

                locData = new MyLocationData.Builder()
                        .direction(mCurrentDirection).latitude(latLng.latitude)
                        .longitude(latLng.longitude).build();
                mBaiduMap.setMyLocationData(locData);
                if (isFollowLocation) {

                    Log.e("Test", "latitude : " + latLng.latitude);
                    Log.e("Test", "longitude : " + latLng.longitude);

                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(latLng);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }
            }
        }
    }

    /**
     * 添加点、线、多边形、圆、文字
     */
    public void addCustomElementsDemo(){
        // 添加普通折线绘制
        List<LatLng> points = new ArrayList<LatLng>();

        points.add(new LatLng(34.80757935795522,113.54127999856813));
        points.add(new LatLng(34.80760158686137,113.53941152310738));
        points.add(new LatLng(34.807623815761445,113.53747118320582));
        points.add(new LatLng(34.807623815761445,113.53687830156923));
        points.add(new LatLng(34.80763122539348,113.53644711492444));
        points.add(new LatLng(34.80950584069348,113.53618660632655));
        points.add(new LatLng(34.81129891071386,113.53593508078376));
        points.add(new LatLng(34.812232477038705,113.53580033495726));
        points.add(new LatLng(34.81252143588087,113.53575541968176));
        points.add(new LatLng(34.81392916946004,113.53555779246956));
        points.add(new LatLng(34.81392916946004,113.53577338579196));
        points.add(new LatLng(34.81392176039946,113.53627643687754));
        points.add(new LatLng(34.81391435133823,113.53702203045083));
        points.add(new LatLng(34.81391435133823,113.53742626793031));
        points.add(new LatLng(34.81391435133823,113.53743525098542));
        points.add(new LatLng(34.81390694227632,113.53934864172169));
        points.add(new LatLng(34.81389953321374,113.53983372669705));
        points.add(new LatLng(34.81388471508655,113.5421244057475));
        points.add(new LatLng(34.81388471508655,113.54289694848609));
        points.add(new LatLng(34.81387730602195,113.54341796568187));
        points.add(new LatLng(34.81387730602195,113.54366949122466));
        points.add(new LatLng(34.81387730602195,113.54367847427976));
        points.add(new LatLng(34.81387730602195,113.54401084731846));
        points.add(new LatLng(34.81386248789074,113.54727169631968));
        points.add(new LatLng(34.81385507882411,113.54774779823997));
        points.add(new LatLng(34.81385507882411,113.54794542545216));
        points.add(new LatLng(34.81384026068886,113.54852932403365));
        points.add(new LatLng(34.81380321533898,113.5504427147699));
        points.add(new LatLng(34.81380321533898,113.550451697825));
        points.add(new LatLng(34.81380321533898,113.55070322336779));
        points.add(new LatLng(34.8137883971943,113.55117034223298));
        points.add(new LatLng(34.81376616997226,113.55281424131624));
        points.add(new LatLng(34.8137587608969,113.55329932629164));
        points.add(new LatLng(34.813751351820876,113.55334424156713));
        points.add(new LatLng(34.813751351820876,113.55347898739363));
        points.add(new LatLng(34.81374394274417,113.55376644515682));
        points.add(new LatLng(34.81371430643063,113.55486237787899));
        points.add(new LatLng(34.81370689735057,113.55510492036669));
        points.add(new LatLng(34.8136772610236,113.55625475141946));
        points.add(new LatLng(34.813655033771305,113.55726983664573));
        points.add(new LatLng(34.81364021559975,113.55801543021902));
        points.add(new LatLng(34.813632806512956,113.5586262779658));
        points.add(new LatLng(34.8136253974255,113.55915627821669));
        points.add(new LatLng(34.81361798833737,113.55958746486148));
        points.add(new LatLng(34.81358835197812,113.56148288948752));
        points.add(new LatLng(34.81357353379445,113.5623632288873));
        points.add(new LatLng(34.81357353379445,113.5624440763832));
        points.add(new LatLng(34.813566124701616,113.5629561105239));
        points.add(new LatLng(34.81355130651393,113.56389034825428));
        points.add(new LatLng(34.813543897419066,113.56486051820505));
        points.add(new LatLng(34.81353648832354,113.56525577262944));
        points.add(new LatLng(34.81352167013046,113.56733984141259));
        points.add(new LatLng(34.81351426103293,113.56799560443486));
        points.add(new LatLng(34.81351426103293,113.56831001136337));
        points.add(new LatLng(34.81349203373626,113.56949577463654));
        points.add(new LatLng(34.81347721553513,113.57043899542201));
        points.add(new LatLng(34.81346239733129,113.57127441954628));
        points.add(new LatLng(34.813454988228365,113.57159780952988));
        points.add(new LatLng(34.813454988228365,113.57192119951347));
        points.add(new LatLng(34.81344757912478,113.57425679383941));
        points.add(new LatLng(34.81339571538077,113.57445442105161));
        points.add(new LatLng(34.81329939691168,113.57459814993321));
        points.add(new LatLng(34.813232714828054,113.5747059465944));
        points.add(new LatLng(34.813069713949915,113.5748406924209));
        points.add(new LatLng(34.81280298453835,113.5748406924209));
        points.add(new LatLng(34.81249920831393,113.5748406924209));
        points.add(new LatLng(34.81228434152157,113.574849675476));
        points.add(new LatLng(34.81160269140375,113.574849675476));
        points.add(new LatLng(34.81110626871518,113.574849675476));
        points.add(new LatLng(34.81075062075438,113.574849675476));
        points.add(new LatLng(34.81041719938435,113.574849675476));
        points.add(new LatLng(34.8096762581324,113.574849675476));
        points.add(new LatLng(34.809646620342484,113.574849675476));
        points.add(new LatLng(34.80947620284172,113.574849675476));
        points.add(new LatLng(34.8093576513271,113.5748586585311));
        points.add(new LatLng(34.80880934833323,113.5748586585311));
        points.add(new LatLng(34.80713477856177,113.5748586585311));
        points.add(new LatLng(34.80641603675676,113.5748586585311));
        points.add(new LatLng(34.80620115395795,113.5748586585311));
        points.add(new LatLng(34.806119646541546,113.5748586585311));
        points.add(new LatLng(34.805519453061365,113.5748676415862));
        points.add(new LatLng(34.80500817365489,113.5748766246413));
        points.add(new LatLng(34.803985605240214,113.5748856076964));
        points.add(new LatLng(34.80397819527746,113.5748856076964));
        points.add(new LatLng(34.80392632551926,113.5748676415862));
        points.add(new LatLng(34.80378553600951,113.5747867940903));
        points.add(new LatLng(34.80375589608179,113.574759844925));
        points.add(new LatLng(34.803718846157025,113.5747239127046));
        points.add(new LatLng(34.803637336263385,113.57466103131891));
        points.add(new LatLng(34.80351877627289,113.5746161160434));
        points.add(new LatLng(34.80342985616707,113.57462509909851));
        points.add(new LatLng(34.80331129587542,113.5747328957597));
        points.add(new LatLng(34.8032594256937,113.57497543824739));
        points.add(new LatLng(34.8030815848206,113.57521798073509));
        points.add(new LatLng(34.802785182504984,113.57546950627788));
        points.add(new LatLng(34.8030815848206,113.57521798073509));
        points.add(new LatLng(34.802785182504984,113.57546950627788));
        points.add(new LatLng(34.802607340599344,113.57562221821458));
        points.add(new LatLng(34.80207381255918,113.57607137096957));
        points.add(new LatLng(34.80175517609534,113.57634086262256));
        points.add(new LatLng(34.80148099953855,113.57658340511026));
        points.add(new LatLng(34.80117718119804,113.57684391370815));
        points.add(new LatLng(34.80097710508841,113.57701459175505));
        points.add(new LatLng(34.800250898795426,113.57763442255693));
        points.add(new LatLng(34.79992484489042,113.57791289726502));
        points.add(new LatLng(34.79984333121082,113.57798476170582));
        points.add(new LatLng(34.799761817449905,113.57805662614662));
        points.add(new LatLng(34.7963307553883,113.58100306821935));
        points.add(new LatLng(34.795248792328,113.58193730594972));
        points.add(new LatLng(34.79520432778613,113.58197323817012));
        points.add(new LatLng(34.79373698432965,113.58312306922289));
        points.add(new LatLng(34.79373698432965,113.583132052278));
        points.add(new LatLng(34.79211398284531,113.58444357832256));
        points.add(new LatLng(34.79183977393183,113.58466815470005));
        points.add(new LatLng(34.79059470566932,113.58573713825693));
        points.add(new LatLng(34.79059470566932,113.58573713825693));
        points.add(new LatLng(34.790661406235735,113.58583595186303));
        points.add(new LatLng(34.79044648199351,113.58602459602012));
        points.add(new LatLng(34.78977947212536,113.58659052849141));
        points.add(new LatLng(34.78912727943469,113.5871564609627));
        points.add(new LatLng(34.78894940779753,113.5873091728994));
        points.add(new LatLng(34.78889752849712,113.58735408817489));
        points.add(new LatLng(34.788163803435964,113.58797391897677));
        points.add(new LatLng(34.78786734699734,113.58822544451957));
        points.add(new LatLng(34.787504186394486,113.58853985144806));
        points.add(new LatLng(34.78731148827646,113.58870154643986));
        points.add(new LatLng(34.78725219645644,113.58875544477046));
        points.add(new LatLng(34.78673339119635,113.58920459752545));
        points.add(new LatLng(34.78651104507674,113.58928544502135));
        points.add(new LatLng(34.7865184566238,113.58939324168254));
        points.add(new LatLng(34.78652586817021,113.59075866605771));
        points.add(new LatLng(34.78652586817021,113.5909383271597));
        points.add(new LatLng(34.786533279715925,113.5911808696474));
        points.add(new LatLng(34.786503633529,113.59198934460638));
        points.add(new LatLng(34.7864888104315,113.59258222624297));
        points.add(new LatLng(34.786481398881755,113.59277087040006));
        points.add(new LatLng(34.786473987331334,113.59342663342234));
        points.add(new LatLng(34.78627387521576,113.59459443058533));
        points.add(new LatLng(34.78602929307583,113.59568138025239));
        points.add(new LatLng(34.78588106111966,113.59635510938487));
        points.add(new LatLng(34.78563647780423,113.59746900821725));
        points.add(new LatLng(34.78562165454949,113.59751392349274));
        points.add(new LatLng(34.78540671705334,113.59864578843532));
        points.add(new LatLng(34.785184367324625,113.5995530770004));
        points.add(new LatLng(34.785184367324625,113.5995530770004));
        points.add(new LatLng(34.78516213231849,113.599606975331));
        points.add(new LatLng(34.7849842520516,113.60002917892068));
        points.add(new LatLng(34.784858253295006,113.60069392499807));
        points.add(new LatLng(34.78476931287931,113.60120595913875));
        points.add(new LatLng(34.78476190117361,113.60120595913875));
        points.add(new LatLng(34.78448766759107,113.60299358710361));
        points.add(new LatLng(34.78436166806899,113.6037751128973));
        points.add(new LatLng(34.784287550612326,113.60422426565228));
        points.add(new LatLng(34.78410225667663,113.60540104587035));
        points.add(new LatLng(34.78405778606957,113.60571545279885));
        points.add(new LatLng(34.784042962528495,113.60582324946004));
        points.add(new LatLng(34.78403555075696,113.60583223251514));
        points.add(new LatLng(34.78403555075696,113.60625443610483));
        points.add(new LatLng(34.783835432671296,113.6075839282596));
        points.add(new LatLng(34.783805785505834,113.607799521582));
        points.add(new LatLng(34.78379096191907,113.60792528435339));
        points.add(new LatLng(34.78375390294038,113.60816782684108));
        points.add(new LatLng(34.78367237312814,113.60868884403688));
        points.add(new LatLng(34.78365754951719,113.60879664069807));
        points.add(new LatLng(34.78363531409571,113.60896731874496));
        points.add(new LatLng(34.783435195030165,113.61033274312013));
        points.add(new LatLng(34.78340554771953,113.61053037033233));
        points.add(new LatLng(34.7833981358902,113.61057528560782));
        points.add(new LatLng(34.783383312229496,113.61067409921392));
        points.add(new LatLng(34.78335366490006,113.61084477726082));
        points.add(new LatLng(34.78324989916228,113.6115454555586));
        points.add(new LatLng(34.7832276636299,113.6116891844402));
        points.add(new LatLng(34.78319801624399,113.61191376081769));
        points.add(new LatLng(34.78306460287434,113.61279410021747));
        points.add(new LatLng(34.78304977915317,113.61291986298886));
        points.add(new LatLng(34.783042367291564,113.61295579520926));
        points.add(new LatLng(34.78293860115862,113.61361155823155));
        points.add(new LatLng(34.78292377741459,113.61371935489275));
        points.add(new LatLng(34.78279777548179,113.61454579596193));
        points.add(new LatLng(34.78279036359735,113.61459071123743));
        points.add(new LatLng(34.782775539826424,113.61470749095372));
        points.add(new LatLng(34.78258283055993,113.6159471525575));
        points.add(new LatLng(34.7825087114903,113.61644122058797));
        points.add(new LatLng(34.78245682810159,113.61677359362668));
        points.add(new LatLng(34.78243459235348,113.61690833945316));
        points.add(new LatLng(34.78239012083911,113.61721376332656));
        points.add(new LatLng(34.7822863538782,113.61788749245905));
        points.add(new LatLng(34.7821158792993,113.61870495047313));
        points.add(new LatLng(34.78201211199024,113.619234950724));
        points.add(new LatLng(34.781930580440736,113.6196122390382));
        points.add(new LatLng(34.78190093258439,113.61977393403));
        points.add(new LatLng(34.78190093258439,113.61977393403));
        points.add(new LatLng(34.78187869668507,113.6198547815259));
        points.add(new LatLng(34.78168598530428,113.62069918870527));
        points.add(new LatLng(34.78164151338266,113.62089681591748));
        points.add(new LatLng(34.78152292147338,113.62144478227856));
        points.add(new LatLng(34.78133020925388,113.62228020640283));
        points.add(new LatLng(34.78126350107204,113.62258563027623));
        points.add(new LatLng(34.78114490861437,113.62311563052711));
        points.add(new LatLng(34.78106337619993,113.623465969676));
        points.add(new LatLng(34.780989255752594,113.6239241054861));
        points.add(new LatLng(34.78083360259444,113.6241935971391));
        points.add(new LatLng(34.78071500951313,113.62463376683898));
        points.add(new LatLng(34.78066312498594,113.62472359738996));
        points.add(new LatLng(34.78059641625975,113.62470563127978));
        points.add(new LatLng(34.779810731610766,113.62445410573699));
        points.add(new LatLng(34.77956613013156,113.62437325824108));
        points.add(new LatLng(34.77886938251274,113.62415766491868));
        points.add(new LatLng(34.778743374330595,113.62411274964319));
        points.add(new LatLng(34.778298638017716,113.62397800381669));
        points.add(new LatLng(34.77818745356147,113.62394207159629));
        points.add(new LatLng(34.7777797759283,113.6237983427147));
        points.add(new LatLng(34.77708301308548,113.62355580022701));
        points.add(new LatLng(34.77648260629842,113.62334020690462));
        points.add(new LatLng(34.77610457013349,113.62321444413321));
        points.add(new LatLng(34.775355905119156,113.62295393553532));
        points.add(new LatLng(34.775348492559985,113.62294495248022));
        points.add(new LatLng(34.774918562978534,113.62281020665372));
        points.add(new LatLng(34.77432555294988,113.62261257944152));
        points.add(new LatLng(34.773806665646866,113.62244190139464));
        points.add(new LatLng(34.773332251516905,113.62228918945792));
        points.add(new LatLng(34.77325812406048,113.62226224029264));
        points.add(new LatLng(34.77176073505207,113.62174122309685));
        points.add(new LatLng(34.77162730301784,113.62169630782135));
        points.add(new LatLng(34.771330786606754,113.62158851116016));
        points.add(new LatLng(34.77123441854166,113.62155257893976));
        points.add(new LatLng(34.77092307478598,113.62144478227856));
        points.add(new LatLng(34.77084894514566,113.62141783311326));
        points.add(new LatLng(34.76990007981452,113.62109444312966));
        points.add(new LatLng(34.76941081682631,113.62092376508276));
        points.add(new LatLng(34.768143167286325,113.62048359538288));
        points.add(new LatLng(34.76804679546502,113.62044766316248));
        points.add(new LatLng(34.76797266321752,113.62042071399718));
        points.add(new LatLng(34.767898530902826,113.62039376483189));
        points.add(new LatLng(34.766831018120776,113.6199985104075));
        points.add(new LatLng(34.76674947143266,113.61998952735239));
        points.add(new LatLng(34.766393630387626,113.6199266459667));
        points.add(new LatLng(34.7659562403155,113.6199266459667));
        points.add(new LatLng(34.76533351143153,113.6199356290218));
        points.add(new LatLng(34.764681123706126,113.61994461207689));
        points.add(new LatLng(34.76422148377521,113.61994461207689));
        points.add(new LatLng(34.76377666847968,113.61994461207689));
        points.add(new LatLng(34.76356167355308,113.61995359513199));
        points.add(new LatLng(34.762116003337965,113.6199625781871));
        points.add(new LatLng(34.76202703818254,113.6199625781871));
        points.add(new LatLng(34.76189359026802,113.6199625781871));
        points.add(new LatLng(34.761752728344284,113.6199625781871));
        points.add(new LatLng(34.76044047666349,113.61994461207689));
        points.add(new LatLng(34.759906673346926,113.6199356290218));
        points.add(new LatLng(34.75973615210899,113.6199356290218));
        points.add(new LatLng(34.75972132415847,113.6199356290218));
        points.add(new LatLng(34.75966201232948,113.6199356290218));
        points.add(new LatLng(34.75959528647046,113.6199356290218));
        points.add(new LatLng(34.75917268809961,113.6199356290218));
        points.add(new LatLng(34.75872784535102,113.6199356290218));
        points.add(new LatLng(34.75852766532505,113.6199356290218));
        points.add(new LatLng(34.75820144497401,113.61994461207689));
        points.add(new LatLng(34.758001263660105,113.61994461207689));
        points.add(new LatLng(34.757726940323344,113.61994461207689));
        points.add(new LatLng(34.75746744442852,113.61994461207689));
        points.add(new LatLng(34.757052249284946,113.61994461207689));
        points.add(new LatLng(34.75660739500737,113.61995359513199));
        points.add(new LatLng(34.756377552683,113.61995359513199));
        points.add(new LatLng(34.75627375271198,113.61995359513199));
        points.add(new LatLng(34.756058738067395,113.61995359513199));
        points.add(new LatLng(34.75559904969064,113.6199625781871));
        points.add(new LatLng(34.75523574575937,113.6199625781871));
        points.add(new LatLng(34.75496882755748,113.6199625781871));
        points.add(new LatLng(34.754434988541654,113.6199715612422));
        points.add(new LatLng(34.753708368729384,113.61998054429729));
        points.add(new LatLng(34.753626808959936,113.61998054429729));
        points.add(new LatLng(34.753523005499446,113.61998054429729));
        points.add(new LatLng(34.753523005499446,113.6200703748483));
        points.add(new LatLng(34.75351559096152,113.62122020590107));
        points.add(new LatLng(34.75351559096152,113.62175918920704));
        points.add(new LatLng(34.75350076188365,113.62330427468422));
        points.add(new LatLng(34.753508176422926,113.6236815629984));
        points.add(new LatLng(34.75353042003671,113.62476851266547));
        points.add(new LatLng(34.75355266364445,113.62641241174873));
        points.add(new LatLng(34.75355266364445,113.62642139480384));
        points.add(new LatLng(34.75356749271293,113.62722986976281));
        points.add(new LatLng(34.75357490724615,113.6275981750219));
        points.add(new LatLng(34.75358232177871,113.6281910566585));
        points.add(new LatLng(34.753589736310595,113.62875698912978));
        points.add(new LatLng(34.753597150841806,113.62952054881325));
        points.add(new LatLng(34.753597150841806,113.62952953186836));
        points.add(new LatLng(34.753611979902225,113.63007749822944));
        points.add(new LatLng(34.753626808959936,113.63054461709461));
        points.add(new LatLng(34.7536342234878,113.63085902402312));
        points.add(new LatLng(34.75364163801498,113.63134410899852));
        points.add(new LatLng(34.75364163801498,113.63141597343932));
        points.add(new LatLng(34.75365646706733,113.6317932617535));
        points.add(new LatLng(34.753671296116984,113.63263766893289));
        points.add(new LatLng(34.753708368729384,113.63418275441005));
        points.add(new LatLng(34.75371578324984,113.63445224606303));
        points.add(new LatLng(34.75371578324984,113.63453309355893));
        points.add(new LatLng(34.75373061228875,113.63509004297512));
        points.add(new LatLng(34.75374544132499,113.6357008907219));
        points.add(new LatLng(34.75374544132499,113.6359075009892));
        points.add(new LatLng(34.75375285584208,113.63644648429519));
        points.add(new LatLng(34.75375285584208,113.63668902678289));
        points.add(new LatLng(34.75377509938936,113.63853953613344));
        points.add(new LatLng(34.753782513903786,113.63915038388022));
        points.add(new LatLng(34.7537973429306,113.64063258797168));
        points.add(new LatLng(34.75380475744299,113.64118953738787));
        points.add(new LatLng(34.75380475744299,113.64124343571847));
        points.add(new LatLng(34.75381217195472,113.64215072428355));
        points.add(new LatLng(34.753819586465774,113.64251004648754));
        points.add(new LatLng(34.753819586465774,113.64353411476891));
        points.add(new LatLng(34.75382700097617,113.64422581001159));
        points.add(new LatLng(34.75382700097617,113.64535767495417));
        points.add(new LatLng(34.75383441548588,113.64669615016403));
        points.add(new LatLng(34.75383441548588,113.64681292988034));
        points.add(new LatLng(34.75383441548588,113.64682191293544));
        points.add(new LatLng(34.75383441548588,113.64687581126603));
        points.add(new LatLng(34.753841829994926,113.64712733680882));
        points.add(new LatLng(34.753841829994926,113.64764835400462));
        points.add(new LatLng(34.75382700097617,113.6482142864759));
        points.add(new LatLng(34.753782513903786,113.64942699891436));
        points.add(new LatLng(34.75376768487427,113.65038818581004));
        points.add(new LatLng(34.75376768487427,113.65047801636105));
        points.add(new LatLng(34.75376027035851,113.65083733856504));
        points.add(new LatLng(34.75376027035851,113.65114276243843));
        points.add(new LatLng(34.75375285584208,113.65153801686282));
        points.add(new LatLng(34.75375285584208,113.65196022045251));
        points.add(new LatLng(34.75374544132499,113.65255310208909));
        points.add(new LatLng(34.7537380268072,113.65327174649707));
        points.add(new LatLng(34.7537380268072,113.65398140784995));
        points.add(new LatLng(34.75373061228875,113.65471801836814));
        points.add(new LatLng(34.75373061228875,113.65530191694963));
        points.add(new LatLng(34.75372319776964,113.6563619174514));
        points.add(new LatLng(34.75371578324984,113.65670327354519));
        points.add(new LatLng(34.75371578324984,113.65699073130838));
        points.add(new LatLng(34.75376027035851,113.65743090100827));
        points.add(new LatLng(34.754034606994225,113.65841903706925));
        points.add(new LatLng(34.75405685046492,113.65849090151005));
        points.add(new LatLng(34.75412358084073,113.65867056261204));
        points.add(new LatLng(34.75538403327308,113.66215598799076));
        points.add(new LatLng(34.75617736690669,113.6645275145371));
        points.add(new LatLng(34.756303409859996,113.66489581979619));
        points.add(new LatLng(34.75648876679154,113.66547073532257));
        points.add(new LatLng(34.75735623165023,113.66805785519131));
        points.add(new LatLng(34.757415545150785,113.6682285332382));
        points.add(new LatLng(34.756473938252476,113.66881243181969));
        points.add(new LatLng(34.75612546681066,113.66905497430739));
        points.add(new LatLng(34.75602908083069,113.66911785569309));
        points.add(new LatLng(34.75592528041761,113.66919870318898));
        points.add(new LatLng(34.75510228676726,113.66976463566027));
        points.add(new LatLng(34.755131944339865,113.66956700844807));
        points.add(new LatLng(34.75473156620262,113.6685429401667));
        points.add(new LatLng(34.75473156620262,113.6685339571116));


//        OverlayOptions ooPolyline1 = new PolylineOptions().width(10)
//                .color(0x80FF0000).points(points);
//        mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline1);


                // 添加多纹理分段的折线绘制
        BitmapDescriptor mGreenTexture = BitmapDescriptorFactory.fromAsset("icon_road_green_arrow.png");
        List<BitmapDescriptor> textureList = new ArrayList<BitmapDescriptor>();
        textureList.add(mGreenTexture);
        List<Integer> textureIndexs = new ArrayList<Integer>();
        textureIndexs.add(0);
        OverlayOptions ooPolyline11 = new PolylineOptions().width(20)
                .points(points).dottedLine(true).customTextureList(textureList).textureIndex(textureIndexs);
        mTexturePolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline11);


        // 添加圆点
        LatLng llCircle100 = new LatLng(34.80757935795522,113.54128898162324);
        OverlayOptions ooCircle100 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle100).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle100);
        LatLng llCircle101 = new LatLng(34.807623815761445,113.53747118320582);
        OverlayOptions ooCircle101 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle101).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle101);
        LatLng llCircle102 = new LatLng(34.81390694227632,113.53743525098542);
        OverlayOptions ooCircle102 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle102).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle102);
        LatLng llCircle103 = new LatLng(34.81386248789074,113.54367847427976);
        OverlayOptions ooCircle103 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle103).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle103);
        LatLng llCircle104 = new LatLng(34.81377357904695,113.550451697825);
        OverlayOptions ooCircle104 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle104).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle104);
        LatLng llCircle105 = new LatLng(34.81371430643063,113.55329932629164);
        OverlayOptions ooCircle105 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle105).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle105);
        LatLng llCircle106 = new LatLng(34.81364762468586,113.55726983664573);
        OverlayOptions ooCircle106 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle106).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle106);
        LatLng llCircle107 = new LatLng(34.81355130651393,113.56389034825428);
        OverlayOptions ooCircle107 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle107).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle107);
        LatLng llCircle108 = new LatLng(34.81349944283582,113.56799560443486);
        OverlayOptions ooCircle108 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle108).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle108);
        LatLng llCircle109 = new LatLng(34.813454988228365,113.57192119951347);
        OverlayOptions ooCircle109 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle109).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle109);
        LatLng llCircle110 = new LatLng(34.81228434152157,113.5748676415862);
        OverlayOptions ooCircle110 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle110).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle110);
        LatLng llCircle111 = new LatLng(34.80620115395795,113.5748676415862);
        OverlayOptions ooCircle111 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle111).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle111);
        LatLng llCircle112 = new LatLng(34.80206640242298,113.57606238791448);
        OverlayOptions ooCircle112 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle112).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle112);
        LatLng llCircle113 = new LatLng(34.795241381572716,113.58192832289463);
        OverlayOptions ooCircle113 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle113).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle113);
        LatLng llCircle114 = new LatLng(34.79183977393183,113.58466815470005);
        OverlayOptions ooCircle114 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle114).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle114);
        LatLng llCircle115 = new LatLng(34.7864888104315,113.59258222624297);
        OverlayOptions ooCircle115 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle115).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle115);
        LatLng llCircle116 = new LatLng(34.78476190117361,113.60120595913875);
        OverlayOptions ooCircle116 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle116).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle116);
        LatLng llCircle117 = new LatLng(34.78367237312814,113.60868884403688);
        OverlayOptions ooCircle117 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle117).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle117);
        LatLng llCircle118 = new LatLng(34.783042367291564,113.61291986298886);
        OverlayOptions ooCircle118 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle118).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle118);
        LatLng llCircle119 = new LatLng(34.78190093258439,113.61977393403);
        OverlayOptions ooCircle119 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle119).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle119);
        LatLng llCircle120 = new LatLng(34.78106337619993,113.623465969676);
        OverlayOptions ooCircle120 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle120).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle120);
        LatLng llCircle121 = new LatLng(34.77818745356147,113.6239330885412);
        OverlayOptions ooCircle121 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle121).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle121);
        LatLng llCircle122 = new LatLng(34.775355905119156,113.62296291859042);
        OverlayOptions ooCircle122 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle122).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle122);
        LatLng llCircle123 = new LatLng(34.7659562403155,113.61995359513199);
        OverlayOptions ooCircle123 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle123).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle123);
        LatLng llCircle124 = new LatLng(34.75973615210899,113.6199356290218);
        OverlayOptions ooCircle124 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle124).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle124);
        LatLng llCircle125 = new LatLng(34.75355266364445,113.62642139480384);
        OverlayOptions ooCircle125 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle125).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle125);
        LatLng llCircle126 = new LatLng(34.75360456537235,113.62952953186836);
        OverlayOptions ooCircle126 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle126).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle126);
        LatLng llCircle127 = new LatLng(34.7537380268072,113.6357008907219);
        OverlayOptions ooCircle127 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle127).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle127);
        LatLng llCircle128 = new LatLng(34.753819586465774,113.64251004648754);
        OverlayOptions ooCircle128 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle128).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle128);
        LatLng llCircle129 = new LatLng(34.75380475744299,113.64682191293544);
        OverlayOptions ooCircle129 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle129).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle129);
        LatLng llCircle130 = new LatLng(34.753708368729384,113.6563619174514);
        OverlayOptions ooCircle130 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle130).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle130);
        LatLng llCircle131 = new LatLng(34.75473156620262,113.6685429401667);
        OverlayOptions ooCircle131 = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle131).stroke(new Stroke(5, 0x80000000))
                .radius(14);
        mBaiduMap.addOverlay(ooCircle131);
    }
}

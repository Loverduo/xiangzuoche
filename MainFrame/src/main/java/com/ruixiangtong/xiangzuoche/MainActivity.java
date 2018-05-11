package com.ruixiangtong.xiangzuoche;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ruixiangtong.register.RegisterActivity;
import com.ruixiangtong.xzc.login.LoginActivity;
import com.ruixiangtong.xzc.map.AddOverlayActivity;
import com.ruixiangtong.xzc.map.SetCurBusLineActivity;
import com.ruixiangtong.xzc.map.location.BusLocationShowActivity;
import com.ruixiangtong.xzc.setting.changepassword.ChangePasswordActivity;
import com.ruixiangtong.xzc.setting.zxing.creat.PopupWindowZxing;
import com.ruixiangtong.xzc.setting.zxing.scan.CaptureActivity;
import com.ruixiangtong.xzc.share.ShareActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //分享
        Button shareBtn = (Button) findViewById(R.id.share);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ShareActivity.class);
                startActivity(intent);
            }
        });

        //二维码
        Button shareZXing = (Button) findViewById(R.id.zxing);
        shareZXing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindowZxing popupWindowZxing = new PopupWindowZxing(MainActivity.this, "http://open.weixin.qq.com/download/sdk/gen_signature.apk");
                popupWindowZxing.showPopupWindow();
            }
        });

        //扫描
        Button scan = (Button) findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CaptureActivity.class);
                startActivity(intent);
            }
        });

        //登录
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //注册
        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //修改密码
        Button changepassword = (Button) findViewById(R.id.changepassword);
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        //地图
        Button map = (Button) findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, BusLocationShowActivity.class);
                startActivity(intent);
            }
        });

        //获取位置
        Button getLocation = (Button) findViewById(R.id.getLocation);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AddOverlayActivity.class);
                startActivity(intent);
            }
        });

        //公交线路
        Button showBusLine = (Button) findViewById(R.id.showBusLine);
        showBusLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, BusLocationShowActivity.class);
                startActivity(intent);
            }
        });

        //上传当前位置
        Button upCurLocation = (Button) findViewById(R.id.upCurLocation);
        upCurLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SetCurBusLineActivity.class);
                startActivity(intent);
            }
        });

    }
}

package com.ruixiangtong.xiangzuoche.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.ruixiangtong.common.mvp.BasePresenterActivity;
import com.ruixiangtong.common.utils.LogUtil;
import com.ruixiangtong.common.utils.SharePreferceUtil;
import com.ruixiangtong.xzc.map.location.BusLocationShowActivity;

/**
 * <p>Summary:闪屏页面业务处理</p>
 * <p>Description:</p>
 * <p>Package:com.xdja.actoma.presenter.activity</p>
 * <p>Author:fanjiandong</p>
 * <p>Date:2015/7/30</p>
 * <p>Time:19:09</p>
 */
public class SplashScreenPresenter extends BasePresenterActivity<SplashScreenCommand, SplashScreenVu> implements SplashScreenCommand {

    @Override
    protected Class<? extends SplashScreenVu> getVuClass() {
        return ViewSplashScreen.class;
    }

    @Override
    protected SplashScreenCommand getCommand() {
        return this;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);

        LogUtil.getUtils().i("SplashScreenPresenter  onBindView end");
        Intent intent = new Intent();
        if (SharePreferceUtil.getPreferceUtil(this).getIsFirstUse()) {
            intent.setClass(this, BusLocationShowActivity.class);
            SharePreferceUtil.getPreferceUtil(this).setIsFirstUse(false);
        } else {
            intent.setClass(this, BusLocationShowActivity.class);
        }
        startActivity(intent);
    }

    @Override
    protected void preBindView(Bundle savedInstanceState) {
        super.preBindView(savedInstanceState);
        if (getIntent() != null) {
            //设置导航栏透明(防止背景因导航栏出现拉伸压缩失真)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //设置全屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }
    }
}

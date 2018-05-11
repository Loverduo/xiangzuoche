package com.ruixiangtong.xiangzuoche.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.ruixiangtong.common.mvp.BasePresenterActivity;
import com.ruixiangtong.common.utils.SharePreferceUtil;
import com.ruixiangtong.xzc.map.location.BusLocationShowActivity;


/**
 * <p>Summary:产品介绍动画</p>
 * <p>Description:</p>
 * <p>Package:com.xdja.actoma.presenter.activity</p>
 * <p>Author:fanjiandong</p>
 * <p>Date:2015/7/30</p>
 * <p>Time:19:09</p>
 */
public class WelComePresenter extends BasePresenterActivity<WelComeCommand, WelComeVu> implements WelComeCommand {
    //是否从设置进入的欢迎页
    public final static String IS_SETTING_WELCOME = "isSettingWelcome";
    private boolean isSettingWelcome = false;
//    private LoginStateCallBack callBack;

    @Override
    protected Class<? extends WelComeVu> getVuClass() {
        return ViewWelCome.class;
    }

    @Override
    protected WelComeCommand getCommand() {
        return this;
    }

    @Override
    protected void preBindView(Bundle savedInstanceState) {
        super.preBindView(savedInstanceState);
        //设置导航栏透明(防止背景因导航栏出现拉伸压缩失真)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //判断是否是从关于安通+进入的
        if (getIntent() != null) {//安通+进入
            isSettingWelcome = getIntent().getBooleanExtra(IS_SETTING_WELCOME, false);
        }
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
        //getVu().showToolBar(isSettingWelcome);
        getVu().isHideWelcomeBtn(false);
        if (isSettingWelcome)
            return;
    }

    protected void hideLoading() {
        getVu().hideLoading();
    }

    /**
     * 立即体验按钮点击事件，进行登录
     * 跳出介绍页，进入到登录界面
     */
    @Override
    public void skipLuncher() {
        SharePreferceUtil.getPreferceUtil(WelComePresenter.this).setIsFirstUse(false);
        Intent intent = new Intent();
        intent.setClass(this, BusLocationShowActivity.class);
        startActivity(intent);
    }
}

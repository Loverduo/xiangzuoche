package com.ruixiangtong.xiangzuoche.splashscreen;


import com.ruixiangtong.common.mvp.ActivityVu;

public interface SplashScreenVu extends ActivityVu<SplashScreenCommand> {

    /**
     * 是否显示重试按钮
     * @param isShow
     */
    void isShowAgainBtn(boolean isShow);
}

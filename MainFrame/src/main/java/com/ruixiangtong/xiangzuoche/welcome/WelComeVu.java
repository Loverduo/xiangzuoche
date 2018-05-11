package com.ruixiangtong.xiangzuoche.welcome;


import com.ruixiangtong.common.mvp.ActivityVu;

/**
 * Created by chenbing on 2015/7/29.
 */
public interface WelComeVu extends ActivityVu<WelComeCommand> {

    /**
     * 是否隐藏介绍动画上的 立即体验 按钮
     *
     * @param isHide
     */
    void isHideWelcomeBtn(boolean isHide);

    /**
     * 隐藏进度动画
     */
    void hideLoading();

}

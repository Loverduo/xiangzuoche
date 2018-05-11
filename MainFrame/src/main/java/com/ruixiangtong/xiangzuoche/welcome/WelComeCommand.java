package com.ruixiangtong.xiangzuoche.welcome;


import com.ruixiangtong.common.mvp.Command;

/**
 * Created by chenbing on 2015/7/29.
 */
public interface WelComeCommand extends Command {

    /**
     * 跳出介绍页
     */
    void skipLuncher();
}

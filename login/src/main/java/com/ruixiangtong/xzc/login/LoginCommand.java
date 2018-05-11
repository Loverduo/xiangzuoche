package com.ruixiangtong.xzc.login;


import com.ruixiangtong.common.mvp.Command;

/**
 * Created by XURJ on 2016/5/31.
 */
public interface LoginCommand extends Command {
    /**
     * 跳转到注册界面
     */
    void skipToRegister();

    /**
     * 跳转到找回密码界面
     */
    void skipToGetPassword();

    /**
     * 登录
     * @param rxtAccount 瑞享通账号
     * @param password 密码
     */
    void login(String rxtAccount, String password);
}

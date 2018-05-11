package com.ruixiangtong.xzc.setting.changepassword;


import com.ruixiangtong.common.mvp.Command;

/**
 * Created by XURJ on 2016/5/31.
 */
public interface ChangePasswordCommand extends Command {
    /**
     * 跳转到登录界面
     */
    void skipToLogin();

    /**
     * 跳转到找回密码界面
     */
    void skipToGetPassword();

    /**
     * 登录
     * @param phoneNum 手机号
     * @param password 密码
     * @param invitationCode 邀请码
     */
    void register(String phoneNum, String password, String invitationCode);
}

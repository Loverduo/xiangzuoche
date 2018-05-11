package com.ruixiangtong.xzc.setting.changepassword;

import android.content.Intent;
import android.os.Bundle;

import com.ruixiangtong.common.mvp.BasePresenterActivity;


public class ChangePasswordActivity extends BasePresenterActivity<ChangePasswordCommand, ChangePasswordVu> implements ChangePasswordCommand {

    @Override
    protected Class<? extends ChangePasswordVu> getVuClass() {
        return ViewChangePassword.class;
    }

    @Override
    protected ChangePasswordCommand getCommand() {
        return this;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
    }

    @Override
    public void skipToLogin() {
        Intent intent = new Intent();
        intent.setClass(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void skipToGetPassword() {

    }

    @Override
    public void register(String phoneNum, String password, String invitationCode) {

    }


}

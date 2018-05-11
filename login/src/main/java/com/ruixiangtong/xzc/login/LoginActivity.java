package com.ruixiangtong.xzc.login;

import android.content.Intent;
import android.os.Bundle;

import com.ruixiangtong.common.mvp.BasePresenterActivity;
import com.ruixiangtong.register.RegisterActivity;
import com.ruixiangtong.xzc.setting.changepassword.ChangePasswordActivity;


public class LoginActivity extends BasePresenterActivity<LoginCommand, LoginVu> implements LoginCommand {

    @Override
    protected Class<? extends LoginVu> getVuClass() {
        return ViewLogin.class;
    }

    @Override
    protected LoginCommand getCommand() {
        return this;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
    }

    @Override
    public void skipToRegister() {
        Intent intent = new Intent();
        intent.setClass(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void skipToGetPassword() {
        Intent intent = new Intent();
        intent.setClass(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void login(String rxtAccount, String password) {

    }
}

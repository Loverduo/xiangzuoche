package com.ruixiangtong.register;

import android.content.Intent;
import android.os.Bundle;

import com.ruixiangtong.common.mvp.BasePresenterActivity;

public class RegisterActivity extends BasePresenterActivity<RegisterCommand, RegisterVu> implements RegisterCommand {

    @Override
    protected Class<? extends RegisterVu> getVuClass() {
        return ViewRegister.class;
    }

    @Override
    protected RegisterCommand getCommand() {
        return this;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
    }

    @Override
    public void skipToLogin() {
        Intent intent = new Intent();
        intent.setClass(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void skipToGetPassword() {

    }

    @Override
    public void register(String phoneNum, String password, String invitationCode) {

    }


}

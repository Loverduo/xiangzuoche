package com.ruixiangtong.xzc.shareresult;

import android.content.Intent;
import android.os.Bundle;

import com.ruixiangtong.common.mvp.BasePresenterActivity;


public class ShareResultActivity extends BasePresenterActivity<ShareResultCommand, ShareResultVu> implements ShareResultCommand {

    @Override
    protected Class<? extends ShareResultVu> getVuClass() {
        return ViewShareResult.class;
    }

    @Override
    protected ShareResultCommand getCommand() {
        return this;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
    }

    @Override
    public void skipToLogin() {
        Intent intent = new Intent();
        intent.setClass(this, ShareResultActivity.class);
        startActivity(intent);
    }

    @Override
    public void skipToGetPassword() {

    }

    @Override
    public void register(String phoneNum, String password, String invitationCode) {

    }


}

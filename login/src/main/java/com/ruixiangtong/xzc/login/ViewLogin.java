package com.ruixiangtong.xzc.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ruixiangtong.common.mvp.BaseActivityVu;

import butterknife.ButterKnife;

/**
 * Created by XURJ on 2016/5/31.
 */
public class ViewLogin extends BaseActivityVu<LoginCommand> implements LoginVu, View.OnClickListener {
    private Button registerBtn;
    private Button changePasswordBtn;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        super.init(inflater, container);
        initView();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected int getToolbarType() {
        return ToolbarDef.NAVIGATE_BACK;
    }

    @Override
    protected int getToolBarId() {
        return R.id.login_tool_bar;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registerBtn) {
            getCommand().skipToRegister();
        }else if(v.getId() == R.id.changePasswordBtn){
            getCommand().skipToGetPassword();
        }
    }

    private void initView() {
        registerBtn = ButterKnife.findById(getView(), R.id.registerBtn);
        registerBtn.setOnClickListener(this);

        changePasswordBtn = ButterKnife.findById(getView(), R.id.changePasswordBtn);
        changePasswordBtn.setOnClickListener(this);
    }
}

package com.ruixiangtong.xzc.setting.changepassword;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ruixiangtong.common.mvp.BaseActivityVu;
import com.ruixiangtong.xzc.setting.R;

/**
 * Created by XURJ on 2016/5/31.
 */
public class ViewChangePassword extends BaseActivityVu<ChangePasswordCommand> implements ChangePasswordVu {

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        super.init(inflater, container);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_change_password;
    }

    @Override
    protected int getToolbarType() {
        return ToolbarDef.NAVIGATE_BACK;
    }

    @Override
    protected int getToolBarId() {
        return R.id.changeMsg_tool_bar;
    }

}

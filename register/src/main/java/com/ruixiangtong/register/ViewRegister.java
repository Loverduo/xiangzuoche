package com.ruixiangtong.register;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ruixiangtong.common.mvp.BaseActivityVu;


/**
 * Created by XURJ on 2016/5/31.
 */
public class ViewRegister extends BaseActivityVu<RegisterCommand> implements RegisterVu {

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        super.init(inflater, container);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_register;
    }

    @Override
    protected int getToolbarType() {
        return ToolbarDef.NAVIGATE_BACK;
    }

    @Override
    protected int getToolBarId() {
        return R.id.register_tool_bar;
    }
}

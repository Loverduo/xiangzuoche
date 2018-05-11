package com.ruixiangtong.xzc.shareresult;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ruixiangtong.common.mvp.BaseActivityVu;
import com.ruixiangtong.xzc.share.R;

/**
 * Created by XURJ on 2016/5/31.
 */
public class ViewShareResult extends BaseActivityVu<ShareResultCommand> implements ShareResultVu {

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        super.init(inflater, container);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_share;
    }

    @Override
    protected int getToolbarType() {
        return ToolbarDef.NAVIGATE_BACK;
    }

    @Override
    protected int getToolBarId() {
        return R.id.share_tool_bar;
    }
}

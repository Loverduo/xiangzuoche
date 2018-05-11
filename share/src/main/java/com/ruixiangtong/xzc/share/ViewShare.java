package com.ruixiangtong.xzc.share;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.ruixiangtong.common.mvp.BaseActivityVu;
import com.ruixiangtong.common.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by XURJ on 2016/5/31.
 */
public class ViewShare extends BaseActivityVu<ShareCommand> implements ShareVu, View.OnClickListener, AdapterView.OnItemClickListener {
    private GridView gridView;
    private ShareTypeAdapter adapter;

    private Button rewardRule;
    private Button shareMore;
    private Button shareResult;

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        super.init(inflater, container);
        LogUtil.getUtils().d("init()");
        initView();
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

    private void initView() {
        gridView = (GridView) getView().findViewById(R.id.shareType);
        gridView.setOnItemClickListener(this);

        rewardRule = (Button) getView().findViewById(R.id.rewardRule);
        rewardRule.setOnClickListener(this);

        shareMore = (Button) getView().findViewById(R.id.shareMore);
        shareMore.setOnClickListener(this);

        shareResult = (Button) getView().findViewById(R.id.shareResult);
        shareResult.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //奖励规则
        if (v.getId() == R.id.rewardRule) {
            getCommand().skipToRewardRule();
        }

        //显示更多邀请方式
        else if (v.getId() == R.id.shareMore) {
            getCommand().showMoreShareType();
        }

        //邀请结果
        else if (v.getId() == R.id.shareResult) {
            getCommand().skipToShareResult();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getCommand().onClickGridView(parent, view, position, id);
    }

    @Override
    public void setShareTypeList(ArrayList<ShareTypeInfo> shareTypeList) {
        if (adapter == null) {
            adapter = new ShareTypeAdapter(getCommand().getShareTypeList(), getCommand(), getActivity());
            gridView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}

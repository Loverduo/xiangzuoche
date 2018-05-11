package com.ruixiangtong.xzc.share;

import android.view.View;
import android.widget.AdapterView;

import com.ruixiangtong.common.mvp.Command;

import java.util.ArrayList;

/**
 * Created by XURJ on 2016/5/31.
 */
public interface ShareCommand extends Command {
    /**
     * 跳转到奖励规则界面
     */
    void skipToRewardRule();

    /**
     * 显示更多的邀请方式
     */
    void showMoreShareType();

    /**
     * 跳转到邀请结果界面
     */
    void skipToShareResult();

    /**
     * 根据选择类型进行分享
     * @param type
     */
    void shareByType(int type);

    /**
     * 获取分享类型的链表
     * @return
     */
    ArrayList<ShareTypeInfo> getShareTypeList();

    /**
     * 点击邀请方式九宫格
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    void onClickGridView(AdapterView<?> parent, View view, int position, long id);
}

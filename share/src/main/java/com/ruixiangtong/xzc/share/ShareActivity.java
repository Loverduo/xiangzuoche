package com.ruixiangtong.xzc.share;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.ruixiangtong.common.mvp.BasePresenterActivity;
import com.ruixiangtong.common.utils.LogUtil;
import com.ruixiangtong.xzc.shareresult.ShareResultActivity;

import java.util.ArrayList;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


public class ShareActivity extends BasePresenterActivity<ShareCommand, ShareVu> implements ShareCommand {
    private ShareModel model;

    @Override
    protected Class<? extends ShareVu> getVuClass() {
        return ViewShare.class;
    }

    @Override
    protected ShareCommand getCommand() {
        return this;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
        LogUtil.getUtils().d("onBindView()");
//        model = new ShareModel();
//        model.addShareType("微信好友", R.drawable.weixin2, ShareType.WX_FRIEND);
//        model.addShareType("微信朋友圈", R.drawable.pyq, ShareType.WX_PYQ);
//        model.addShareType("面对面推荐", R.drawable.erweima, ShareType.EWM);
//
//        getVu().setShareTypeList(model.getShareTypeList());
    }

    @Override
    public void skipToRewardRule() {

    }

    @Override
    public void showMoreShareType() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题1");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.baidu.com");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.baidu.com");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("应用名称");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.baidu.com");

// 启动分享GUI
        oks.show(this);
    }

    @Override
    public void skipToShareResult() {
        Intent intent = new Intent();
        intent.setClass(this, ShareResultActivity.class);
        startActivity(intent);
    }

    @Override
    public void shareByType(int type) {
        switch (type) {

        }
    }

    @Override
    public ArrayList<ShareTypeInfo> getShareTypeList() {
        return model.getShareTypeList();
    }

    @Override
    public void onClickGridView(AdapterView<?> parent, View view, int position, long id) {

    }
}

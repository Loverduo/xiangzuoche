package com.ruixiangtong.xzc.share;

import java.util.ArrayList;

/**
 * Created by xurj on 2016/7/4.
 */
public class ShareModel {
    private ArrayList<ShareTypeInfo> shareTypeList;

    public ArrayList<ShareTypeInfo> getShareTypeList() {
        return shareTypeList;
    }

    public void setShareTypeList(ArrayList<ShareTypeInfo> shareTypeList) {
        this.shareTypeList = shareTypeList;
    }

    public void addShareType(String showName, int iconId, int type){
        if(shareTypeList == null){
            shareTypeList = new ArrayList<>();
        }

        ShareTypeInfo shareTypeInfo = new ShareTypeInfo(showName, iconId, type);
        shareTypeList.add(shareTypeInfo);
    }
}

package com.ruixiangtong.xzc.share;

/**
 * Created by xurj on 2016/7/3.
 */
public class ShareTypeInfo {
    private String showName;
    private int iconId;
    private int shareType;

    public ShareTypeInfo(String showName, int iconId, int shareType){
        this.showName = showName;
        this.iconId = iconId;
        this.shareType = shareType;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getShareType() {
        return shareType;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }
}

package com.ruixiangtong.xzc.share;


import com.ruixiangtong.common.mvp.ActivityVu;

import java.util.ArrayList;

/**
 * Created by XURJ on 2016/5/31.
 */
public interface ShareVu extends ActivityVu<ShareCommand> {
    void setShareTypeList(ArrayList<ShareTypeInfo> shareTypeList);
}

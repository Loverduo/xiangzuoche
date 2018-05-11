package com.ruixiangtong.xiangzuoche.splashscreen;

import android.view.View;
import android.widget.TextView;

import com.ruixiangtong.common.mvp.BaseActivityVu;
import com.ruixiangtong.common.mvp.ContentView;
import com.ruixiangtong.xiangzuoche.R;

import butterknife.InjectView;

/**
 * Created by chenbing 2015-7-29
 * 闪屏页面
 */
@ContentView(value = R.layout.activity_shut)
public class ViewSplashScreen extends BaseActivityVu<SplashScreenCommand> implements SplashScreenVu {
    @InjectView(R.id.again_btn)
    TextView againBtn;


    @Override
    public void onCreated() {
        super.onCreated();
    }

    @Override
    public void isShowAgainBtn(boolean isShow) {
        if (isShow)againBtn.setVisibility(View.VISIBLE);
        else
            againBtn.setVisibility(View.GONE);
    }
}

package com.ruixiangtong.xiangzuoche.welcome;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruixiangtong.common.mvp.BaseActivityVu;
import com.ruixiangtong.common.mvp.ContentView;
import com.ruixiangtong.xiangzuoche.R;
import com.ruixiangtong.xiangzuoche.welcome.view.FlipPoint;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by chenbing 2015-7-28
 * 产品介绍页
 */
@ContentView(value = R.layout.activity_productintroduce)
public class ViewWelCome extends BaseActivityVu<WelComeCommand> implements WelComeVu {

    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.welcome_start_step_btn)
    TextView welcomeStartStepBtn;
    @InjectView(R.id.flippoint)
    FlipPoint flippoint;


    //介绍界面
    private ArrayList<View> views;

    private int[] images =
            {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3, R.drawable.guide_4};

    private BasePagerAdapter pagerAdapter;

    //是否隐藏欢迎按钮
    private boolean isHideWelcomeBtn = false;

    @Override
    public void onCreated() {
        super.onCreated();
        //设置toolbar背景颜色透明
        //toolbar.setBackgroundColor(getContext().getResources().getColor(com.xdja.contact.R.color.transparent));
        initView();
//        ((ActionBarActivity) getActivity()).getSupportActionBar().hide();
    }

    // 初始化视图
    private void initView() {
        // 实例化视图控件
        views = new ArrayList<View>();
        for (int i = 0; i < images.length; i++) {
            // 循环加入图片
            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(images[i]);
            views.add(imageView);
        }

        pagerAdapter = new BasePagerAdapter(views);
        viewpager.setAdapter(pagerAdapter); // 设置适配器
        viewpager.setOnPageChangeListener(pageChangeListener);
    }

    //viewpager的改变事件
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //如果欢迎按钮显示，就进行淡入淡出功能
            if (!isHideWelcomeBtn) {
                if (position == images.length - 2) {
                    if (positionOffset > 0) {
                        welcomeStartStepBtn.setAlpha(positionOffset);
                        flippoint.setAlpha(1 - positionOffset);
                    }
                }
                if (position != images.length - 1)
                    welcomeStartStepBtn.setEnabled(false);
                else welcomeStartStepBtn.setEnabled(true);
            } else {
                welcomeStartStepBtn.setEnabled(false);
            }
        }

        @Override
        public void onPageSelected(int position) {
            flippoint.setIndex(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    //判断是否从设置该中进入欢迎页
    @OnClick(R.id.welcome_start_step_btn)
    public void startUseBtnClick() {
        boolean isSettingWelcome = getActivity().getIntent().getBooleanExtra(WelComePresenter.IS_SETTING_WELCOME, false);
        if (isSettingWelcome) {
            //从设置中进入欢迎页
            getActivity().finish();
        } else {
            //首次进入安通+欢迎页
            getCommand().skipLuncher();
        }
    }


    @Override
    public void isHideWelcomeBtn(boolean isHide) {
        isHideWelcomeBtn = isHide;
    }

    @Override
    public void hideLoading() {
//        dismissCommonProgressDialog();
    }

//    /**
//     * 是否显示导航栏
//     *
//     * @param isShow
//     */
//    @Override
//    public void showToolBar(boolean isShow) {
//        if (isShow) {
//            ((ActionBarActivity) getActivity()).getSupportActionBar().show();
//        }
//    }

    //引导页使用的pageview适配器
    public class BasePagerAdapter extends PagerAdapter {
        private List<View> views = new ArrayList<View>();

        public BasePagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(views.get(position));
            return views.get(position);
        }
    }

//    @Override
//    protected int getToolbarType() {
//        return ToolbarDef.NAVIGATE_BACK;
//    }
}

package com.ruixiangtong.common.mvp;

import android.support.annotation.IntDef;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.ruixiangtong.common.R;

import butterknife.ButterKnife;

/**
 * <p>Summary:业务Activity的View基类</p>
 * <p>Description:</p>
 * <p>Package:com.xdja.actoma.ui.view</p>
 * <p>Author:fanjiandong</p>
 * <p>Date:2015/7/8</p>
 * <p>Time:10:32</p>
 */

public class BaseActivityVu<T extends Command> extends ActivitySuperView<T> {

    protected Toolbar toolbar;

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        super.init(inflater, container);
        if (getView() != null && getToolBarId() > 0) {
            toolbar = ButterKnife.findById(getView(), getToolBarId());
        }
        if(getCustomView(inflater, container) != null){
            toolbar.setContentInsetsRelative(15, 0);
            toolbar.addView(getCustomView(inflater, container));
        }
    }

    /**
     * 左上角返回按钮回调
     */
    public void onNavigateBackPressed(){

    }

    @Override
    public void onCreated() {
        super.onCreated();
        if (toolbar != null) {
            //设置toolbar显示
            ((ActionBarActivity) getActivity()).setSupportActionBar(toolbar);
            switch (getToolbarType()) {
                //处理导航后退
                case ToolbarDef.NAVIGATE_BACK:
                    toolbar.setNavigationIcon(R.drawable.af_abs_ic_back);
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onNavigateBackPressed();
                            getActivity().finish();
                        }
                    });
                    break;
                case ToolbarDef.NAVIGATE_DEFAULT:
                    ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
                    ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
                    ((ActionBarActivity) getActivity()).getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
                    toolbar.setContentInsetsRelative(15,0);

                    View view = getActivity().getLayoutInflater().inflate(R.layout.view_custom_title, null);
                    ImageView logo = ButterKnife.findById(view, R.id.main_logo);
                    TextView title = ButterKnife.findById(view, R.id.main_title);

                    logo.setImageResource(R.drawable.af_abs_ic_logo);
                    title.setText(getActivity().getTitle());

                    ((ActionBarActivity) getActivity()).getSupportActionBar().setCustomView(view);
                    break;
                case ToolbarDef.NAVIGATE_OTHER:
                    break;
                default:
                    break;
            }
        }
    }

    protected int getToolBarId(){
        return 0;
    }

    protected View getCustomView(LayoutInflater inflater, ViewGroup container){
        return null;
    };

    /**
     * 获得导航显示方式
     *
     * @return 显示方式
     */
    @ToolbarDef.NavigateType
    protected int getToolbarType() {
        return ToolbarDef.NAVIGATE_DEFAULT;
    }

    public static class ToolbarDef {
        /**
         * 导航显示为后退
         */
        public final static int NAVIGATE_BACK = 0;
        /**
         * 默认导航
         */
        public final static int NAVIGATE_DEFAULT = 1;
        /**
         * 未知，留待扩展
         */
        public final static int NAVIGATE_OTHER = 2;

        /**
         * 导航显示类型
         */
        @IntDef({NAVIGATE_BACK, NAVIGATE_DEFAULT, NAVIGATE_OTHER})
        public @interface NavigateType {
        }
    }
}

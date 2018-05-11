package com.ruixiangtong.common.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * <p>Summary:通用Fragment相关的Presenter</p>
 * <p>Description:</p>
 * <p>Package:com.xdja.actoma.presenter.activity</p>
 * <p>Author:fanjiandong</p>
 * <p>Date:2015/7/8</p>
 * <p>Time:10:30</p>
 */
public abstract class BasePresenterFragment<P extends Command, V extends FragmentVu> extends Fragment {

    private V vu;

    public V getVu() {
        return vu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            preBindView(savedInstanceState);
            //初始化View
            vu = getVuClass().newInstance();
            //设置view对业务的调用句柄
            vu.setCommand(getCommand());
            vu.setFragment(this);
            //设置和View关联的Activity
            vu.setActivity(getActivity());
            vu.init(inflater, null);
            vu.onCreated();

            return vu.getView();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onBindView(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        vu.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        vu.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        vu.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        vu.onStop();
    }


    @Override
    public void onDestroyView() {
        vu.onDestroyView();
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        vu.onCreateOptionsMenu(menu, inflater);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        vu.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    protected abstract Class<? extends V> getVuClass();

    protected abstract P getCommand();

    protected void preBindView(Bundle savedInstanceState) {

    }

    protected void onBindView(Bundle savedInstanceState) {

    }


}

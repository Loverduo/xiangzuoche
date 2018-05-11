package com.ruixiangtong.common.mvp;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * <p>Summary:</p>
 * <p>Description:</p>
 * <p>Package:com.xdja.actoma.frame</p>
 * <p>Author:fanjiandong</p>
 * <p>Date:2015/7/9</p>
 * <p>Time:18:45</p>
 */
public abstract class FragmentSuperView<T extends Command> extends SuperView<T> implements FragmentVu<T> {

    private Fragment fragment;

    @Override
    public <A extends Fragment> void setFragment(A fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }

    @Override
    public void onCreated() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroyView() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onOptionsItemSelected(MenuItem item) {

    }
}

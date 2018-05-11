package com.ruixiangtong.common.mvp;

import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by fanjiandong on 2015/5/22.
 * 和Acitivity生命周期相关的View层的生命周期定义
 */
public interface ActivityVu<P extends Command> extends Vu<P> {

    void onCreated();

    void onResume();

    void onStart();

    void onRestart();

    void onPause();

    void onStop();

    void onDestroy();

    void onAttachedToWindow();

    void onDetachedFromWindow();

    void onCreateOptionsMenu(Menu menu);

    void onOptionsItemSelected(MenuItem item);

    void onPrepareOptionsMenu(Menu menu);
}

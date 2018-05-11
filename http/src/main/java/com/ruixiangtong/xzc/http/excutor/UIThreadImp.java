package com.ruixiangtong.xzc.http.excutor;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * <p>Summary:android程序的UI线程</p>
 * <p>Description:</p>
 * <p>Package:com.hysel.picker.excutor</p>
 * <p>Author:fanjiandong</p>
 * <p>Date:2015/6/5</p>
 * <p>Time:15:11</p>
 */
public class UIThreadImp implements PostExecutionThread {

    public UIThreadImp(){}

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}

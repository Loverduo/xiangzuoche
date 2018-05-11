package com.ruixiangtong.xzc.http.usecase;

import com.ruixiangtong.xzc.http.excutor.ExecutorManager;
import com.ruixiangtong.xzc.http.excutor.PostExecutionThread;
import com.ruixiangtong.xzc.http.excutor.ThreadExecutor;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * <p>Summary:业务处理基类</p>
 * <p>Description:</p>
 * <p>Package:com.hysel.picker.excutor</p>
 * <p>Author:fanjiandong</p>
 * <p>Date:2015/6/5</p>
 * <p>Time:14:56</p>
 */
public abstract class UseCase<T> {

    private ThreadExecutor executor;

    private PostExecutionThread mainThread;

    private Subscription subscription = Subscriptions.empty();

    protected UseCase() {
        this(ExecutorManager.getThreadExecutor(), ExecutorManager.getPostExecutionThread());
    }

    protected UseCase(ThreadExecutor executor, PostExecutionThread mainThread) {
        this.executor = executor;
        this.mainThread = mainThread;
    }

    protected abstract Observable<T> buildUseCaseObservable();

    /**
     * 业务执行入口
     *
     * @param useCaseSubscriber 监听处理类
     */
    public void execute(Subscriber<T> useCaseSubscriber) {
        //获取业务处理事件流
        this.subscription = this.buildUseCaseObservable()
                //异步执行业务
                .subscribeOn(Schedulers.from(executor))
                        //在主线程监听
                .observeOn(mainThread.getScheduler())
                        //订阅事件流
                .subscribe(useCaseSubscriber);
    }

    public void execute(Action1<T> action1){
        this.subscription = this.buildUseCaseObservable()
                //异步执行业务
                .subscribeOn(Schedulers.from(executor))
                        //在主线程监听
                .observeOn(mainThread.getScheduler())
                        //订阅事件流
                .subscribe(action1);
    }

    /**
     * 取消业务处理监听
     */
    public void unSubscribe() {
        if (!this.subscription.isUnsubscribed())
            this.subscription.unsubscribe();
    }
}

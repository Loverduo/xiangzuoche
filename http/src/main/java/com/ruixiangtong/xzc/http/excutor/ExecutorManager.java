package com.ruixiangtong.xzc.http.excutor;


/**
 * <p>Summary:</p>
 * <p>Description:</p>
 * <p>Package:com.xdja.actoma.excutor</p>
 * <p>Author:fanjiandong</p>
 * <p>Date:2015/7/6</p>
 * <p>Time:19:49</p>
 */
public class ExecutorManager {

    /**
     * 主线程调度器
     */
    private static PostExecutionThread postExecutionThread;

    /**
     * 异步线程调度器
     */
    private static ThreadExecutor threadExecutor;


    /**
     * {@link #postExecutionThread}
     *
     * @return 获取主线程调度器
     */
    public static PostExecutionThread getPostExecutionThread() {
        if (postExecutionThread == null)
            postExecutionThread = new UIThreadImp();
        return postExecutionThread;
    }

    /**
     * {@link #threadExecutor}
     *
     * @return 获取异步线程调度器
     */
    public static ThreadExecutor getThreadExecutor() {
        if (threadExecutor == null)
            threadExecutor = new ThreadExecutorImp();
        return threadExecutor;
    }
}

package com.weixin.ThreadPool;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.*;

/**
 * @Author lishenshen
 * @Date 2021/1/5
 * @Desc
 */
public class TaskThreadPoolExecutor extends ThreadPoolExecutor {

    private static final String DEFAULT_TASK_NAME = "Default Task";

    public TaskThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public TaskThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public TaskThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public TaskThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                  RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void execute(Runnable command) {
        if (command instanceof TaskRunnable) {
            super.execute(command);
        } else {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            TaskRunnable taskCommand = new TaskRunnable(DEFAULT_TASK_NAME, command, requestAttributes);
            super.execute(taskCommand);
        }
    }
}

package com.weixin.ThreadPool;

import org.slf4j.MDC;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;

/**
 * @Author lishenshen
 * @Date 2021/1/5
 * @Desc
 */
public class TaskRunnable implements Runnable {
    private String taskName;
    private Runnable runnable;
    private Map<String, String> context;
    private RequestAttributes requestAttributes;

    public TaskRunnable(String name, Runnable runnable) {
        this.taskName = name;
        this.runnable = runnable;
        this.context = MDC.getCopyOfContextMap();
    }

    public TaskRunnable(String name, Runnable runnable, RequestAttributes requestAttributes) {
        this.taskName = name;
        this.runnable = runnable;
        this.context = MDC.getCopyOfContextMap();
        this.requestAttributes = requestAttributes;
    }

    @Override
    public void run() {
        try {
            if (this.context != null) {
                MDC.setContextMap(context);
            }
            if (requestAttributes != null) {
                RequestContextHolder.setRequestAttributes(requestAttributes);
            }
            runnable.run();
        } finally {
            if (requestAttributes != null) {
                RequestContextHolder.resetRequestAttributes();
            }
        }
    }
}

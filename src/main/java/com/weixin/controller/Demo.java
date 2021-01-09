package com.weixin.controller;

import java.util.concurrent.*;

/**
 * @Author: lishenshen
 * @Date: 2020/11/19 16:15
 */
public class Demo {

    public static void main(String[] args) {
//        Executors
//        Executor
        ExecutorService executorService = Executors.newScheduledThreadPool(20, new ThreadFactory() {
            final ThreadFactory defaultFactory = Executors.defaultThreadFactory();

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = defaultFactory.newThread(r);
                thread.setName("ShopServiceImpl-" + thread.getName());
                return thread;
            }
        });

    }
}

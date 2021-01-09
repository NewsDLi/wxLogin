package com.weixin.thread;

import java.util.concurrent.Callable;

/**
 * @Author lishenshen
 * @Date 2021/1/4
 * @Desc
 */
public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception{
        String value="test";
        System.out.println("Ready to work");
        Thread.currentThread().sleep(5000);
        System.out.println("task done");
        return  value;
    }

}

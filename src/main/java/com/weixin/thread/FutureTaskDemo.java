package com.weixin.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author lishenshen
 * @Date 2021/1/4
 * @Desc
 */
public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> task = new FutureTask<String>(new MyCallable());
        new Thread(task).start();
        if(!task.isDone()){
            System.out.println("task has not finished, please wait!");
        }
        System.out.println("task return: " + task.get(1000, TimeUnit.MILLISECONDS));

    }
}

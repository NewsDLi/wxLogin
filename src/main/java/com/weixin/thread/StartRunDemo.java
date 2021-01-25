package com.weixin.thread;

/**
 * @Author lishenshen
 * @Date 2021/1/13
 * @Desc
 */
public class StartRunDemo {

    private static void attack() {
        System.out.println("ThreadTest.attack thread name:" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                attack();
            }
        };
        System.out.println("main Thread：" + Thread.currentThread().getName());
        thread.start();
//        thread.run();
    }

}

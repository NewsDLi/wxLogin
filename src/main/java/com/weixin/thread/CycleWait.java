package com.weixin.thread;

/**
 * @Author lishenshen
 * @Date 2021/1/4
 * @Desc
 */
public class CycleWait implements Runnable{
    private String value;
    public void run() {
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        value = "we have data now";
    }

    public static void main1(String[] args) throws InterruptedException {
        CycleWait cw = new CycleWait();
        Thread t = new Thread(cw);
        t.start();
//        while (cw.value == null){
//            Thread.currentThread().sleep(100);
//        }
//        t.join();
        System.out.println("value : " + cw.value);
    }

    // join粗粒度
    public static void main(String[] args) throws InterruptedException {
        CycleWait cw = new CycleWait();
        Thread t1 = new Thread(cw);
        Thread t2 = new Thread(cw);
        t1.start();
        // 等待t1结束，这时候t2线程并未启动
        t1.join();
        System.out.println(t1.getName());
        // t1结束后，启动t2线程
        t2.start();
        t2.join();
        System.out.println(t2.getName());
        System.out.println("value : " + cw.value);
    }
}

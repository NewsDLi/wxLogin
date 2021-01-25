package com.weixin.ThreadPool;

import com.weixin.po.WeixinUserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.*;

/**
 * @Author lishenshen
 * @Date 2021/1/5
 * @Desc
 */
@Controller
public class ThreadPoolDemo {

    private static final ExecutorService executorService = new TaskThreadPoolExecutor(60, 80,
            200L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(400), new ThreadFactory() {
        final ThreadFactory defaultFactory = Executors.defaultThreadFactory();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = defaultFactory.newThread(r);
            thread.setName("ThreadPoolDemo-" + thread.getName());
            return thread;
        }
    }, new ThreadPoolExecutor.AbortPolicy());


    // 微信成功获取到code之后会执行此回调方法
    @RequestMapping("/threadPoolDemo")
    public String threadPoolDemo(HttpServletRequest request) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        // time 10
        CompletableFuture<WeixinUserInfo> userInfoThread1 = CompletableFuture.supplyAsync(() -> buildUserInfo1(10L), executorService);
        // time 30
        CompletableFuture<WeixinUserInfo> userInfoThread2 = CompletableFuture.supplyAsync(() -> buildUserInfo2(30L), executorService);
        // time 40
        CompletableFuture<WeixinUserInfo> userInfoThread3 = CompletableFuture.supplyAsync(() -> buildUserInfo3(40L), executorService);

        CompletableFuture.allOf(userInfoThread1, userInfoThread2, userInfoThread3).get();

        long endtime = System.currentTimeMillis() - startTime;
        System.out.println(endtime);

        // 主线程处理占用时间 50 ms
        long l = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - l >= 50) {
                break;
            }
        }
        // 获取到登录人员信息
        WeixinUserInfo weixinUserInfo = buildUserInfo();
        request.setAttribute("userInfo", weixinUserInfo);
        return "index";
    }

    private WeixinUserInfo buildUserInfo3(long time) {
        // 主线程处理占用时间 50 ms
        long l = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - l >= time) {
                break;
            }
        }
        WeixinUserInfo weixinUserInfo = buildUserInfo();
        return weixinUserInfo;
    }

    private WeixinUserInfo buildUserInfo2(long time) {
        // 主线程处理占用时间 50 ms
        long l = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - l >= time) {
                break;
            }
        }
        WeixinUserInfo weixinUserInfo = buildUserInfo();
        return weixinUserInfo;
    }

    private WeixinUserInfo buildUserInfo1(long time) {
        // 主线程处理占用时间 50 ms
        long l = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - l >= time) {
                break;
            }
        }
        WeixinUserInfo weixinUserInfo = buildUserInfo();
        return weixinUserInfo;
    }

    private WeixinUserInfo buildUserInfo() {
        WeixinUserInfo userInfo = new WeixinUserInfo();
        userInfo.setCity("上海");
        userInfo.setCountry("上海");
        userInfo.setHeadimgurl("www.xxxx.cn");
        userInfo.setNickname("昵称");
        userInfo.setOpenid("openid");
        userInfo.setProvince("上海");
        userInfo.setSex("男");
        userInfo.setUnionid("qwe");
        return userInfo;
    }
}

package com.weixin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weixin.po.WeixinUserInfo;
import com.weixin.service.WeixinLoginService;

@Controller
public class WeixinLogin{

    @Autowired
    private WeixinLoginService weixinLoginService;
    
    // 获取到微信登录需要的code，成功之后执行回调方法callBack
    @RequestMapping("/getCode")
    public void getUserInfo(HttpServletResponse response) throws IOException {
        weixinLoginService.getCode(response);
    }
    
    // 微信成功获取到code之后会执行此回调方法
    @RequestMapping("/callBack")
    public String login(HttpServletRequest request, String code, String state) throws ParseException, IOException {
        // 获取到登录人员信息
        WeixinUserInfo userInfo = weixinLoginService.getUserInfo(code, state);
        request.setAttribute("userInfo", userInfo);
        // 返回index页面
        return "/index";
    }
}

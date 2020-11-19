package com.weixin.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.ParseException;

import com.weixin.po.WeixinUserInfo;

public interface WeixinLoginService{

    void getCode(HttpServletResponse response) throws IOException;

    WeixinUserInfo getUserInfo(String code,String state) throws ParseException, IOException;

}

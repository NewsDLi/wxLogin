package com.weixin.service.impl;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.ParseException;
import org.springframework.stereotype.Service;

import com.weixin.constant.Weixin;
import com.weixin.constant.WeixinParam;
import com.weixin.po.WeixinUserInfo;
import com.weixin.service.WeixinLoginService;
import com.weixin.util.JsonUtils;
import com.weixin.util.WeixinUtil;

import net.sf.json.JSONObject;

@Service
public class WeixinLoginServiceImpl implements WeixinLoginService {

    @Override
    public void getCode(HttpServletResponse response) throws IOException{
     // 组装获取到code的url
        String getCodeUrl = WeixinParam.WEIXIN_GET_CODE_URL.getValue()
                        .replace(WeixinParam.WEIXIN_APPID.getKey(), WeixinParam.WEIXIN_APPID.getValue())
                        // 这里替换微信获取code之后的回调路径地址
                        .replace(WeixinParam.WEIXIN_REDIRECT_URL.getKey(), URLEncoder.encode(WeixinParam.WEIXIN_REDIRECT_URL.getValue()))
                        .replace(WeixinParam.WEIXIN_SNSAPI_USERINFO.getKey(), WeixinParam.WEIXIN_SNSAPI_USERINFO.getValue());
        // 进行转发
        response.sendRedirect(getCodeUrl);
    }

    @Override
    public WeixinUserInfo getUserInfo(String code,String state) throws ParseException, IOException{
     // 组装获取Token的url
        String getTokenUrl = WeixinParam.WEIXIN_GET_ACCESS_TOKEN_URL.getValue()
                        .replace(WeixinParam.WEIXIN_APPID.getKey(), WeixinParam.WEIXIN_APPID.getValue())
                        .replace(WeixinParam.WEIXIN_APPSECRET.getKey(), WeixinParam.WEIXIN_APPSECRET.getValue())
                        .replace(Weixin.CODE, code);
        
        // 进行Get请求，拿到token和openID
        JSONObject doGetStr = WeixinUtil.doGetStr(getTokenUrl);
        String openid = doGetStr.getString(Weixin.OPENID_RESULT);
        String access_token = doGetStr.getString(Weixin.ACCESS_TOKEN_RESULT);
        
        // 组装获取用户的信息的url
        String userInfoUrl = WeixinParam.WEIXIN_GET_USERINFO_URL.getValue()
                        .replace(Weixin.ACCESS_TOKEN, access_token)
                        .replace(Weixin.OPENID, openid);
        
        // 进行get请求，拿到登录人员的信息
        JSONObject userInfo = WeixinUtil.doGetStr(userInfoUrl);
        
        // 对那到的人员信息进行解析，得到一个类对象
        WeixinUserInfo parse = JsonUtils.parse(userInfo.toString(), WeixinUserInfo.class);
        return parse;
    }


}

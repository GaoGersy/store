package com.piesat.project.common.utils;


import com.piesat.project.common.config.WxMpConfig;
import com.piesat.project.common.utils.okhttp.OkHttpUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

@Component
public class WeixinUtils {

    @Autowired
    WxMpConfig mWxMpConfig;

    @Value("#{applicationProperties.baseUrl}")
    private String baseUrl;

    public void getCode(HttpServletResponse response) {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&stat e=STATE#wechat_redirect";
        try {
            String REDIRECT_URI = baseUrl+"user/getUser";
            url = url.replace("APPID", URLEncoder.encode(mWxMpConfig.getAppid(), "utf-8"));
            url = url.replace("REDIRECT_URI", URLEncoder.encode(REDIRECT_URI, "utf-8"));
            response.sendRedirect(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=AppId&secret=AppSecret&code=CODE&grant_type=authorization_code";
        try {
            url = url.replace("AppId", mWxMpConfig.getAppid())
                    .replace("AppSecret", mWxMpConfig.getAppsecret())
                    .replace("CODE", code);
            String weixinResult = OkHttpUtils.getSync(url);
            SuperLogger.e(weixinResult);
            String openId = JSONUtils.getString(weixinResult, "openid");
            return openId;
        } catch (Exception e) {
            SuperLogger.e(e);
            return null;
        }
    }
}

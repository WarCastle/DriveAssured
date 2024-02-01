package com.castle.drive.util.api;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.castle.drive.config.properties.WxMpProperties;
import com.castle.drive.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YuLong
 * @Date 2024/1/31 18:21
 * @Classname WxMpApi
 * @Description 微信小程序接口调用工具类
 */
@Slf4j
@Component
public class WxMpApi {

    private static WxMpProperties wxMpProperties;

    public WxMpApi(WxMpProperties wxMpProperties) {
        log.info("wxMpProperties：" + wxMpProperties);
        WxMpApi.wxMpProperties = wxMpProperties;
    }

    /**
     * 微信小程序登录
     * @param code
     * @return
     */
    public static String getOpenid(String code) {
        log.info("微信小程序code：" + code);
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid", wxMpProperties.getAppid());
        paramMap.put("secret", wxMpProperties.getSecret());
        paramMap.put("js_code", code);
        paramMap.put("grant_type", "authorization_code");
        String result = HttpUtil.get(url, paramMap);
        log.info("微信小程序登录结果：" + result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String openid = jsonObject.getString("openid");
        log.info("微信小程序登录openid：" + openid);
        String errCode = jsonObject.getString("errCode");
        String errMsg = jsonObject.getString("errMsg");
        if (StringUtils.isBlank(openid)) {
            log.error("微信小程序登录失败，errCode: {}, errMsg: {}", errCode, errMsg);
            throw new BusinessException("微信小程序登录失败");
        }
        return openid;
    }
}

package com.piesat.project.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Binary Wang
 *
 */
@Configuration
public class WxMpConfig {
  @Value("#{wxProperties.token}")
  private String token;

  @Value("#{wxProperties.appid}")
  private String appid;

  @Value("#{wxProperties.appsecret}")
  private String appsecret;

  @Value("#{wxProperties.aeskey}")
  private String aesKey;

  public String getToken() {
    return this.token;
  }

  public String getAppid() {
    return this.appid;
  }

  public String getAppsecret() {
    return this.appsecret;
  }

  public String getAesKey() {
    return this.aesKey;
  }

  public void setToken(String token){
    this.token = token;
  }

}

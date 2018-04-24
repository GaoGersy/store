package com.piesat.project.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties()
@PropertySource("classpath:config/application.properties")
public class ApplicationProperties {
    private String defaultMsgKeyWord;
    private String baseUrl;
    private String baseWebUrl;

    public String getDefaultMsgKeyWord() {
        return defaultMsgKeyWord;
    }

    public void setDefaultMsgKeyWord(String defaultMsgKeyWord) {
        this.defaultMsgKeyWord = defaultMsgKeyWord;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseWebUrl() {
        return baseWebUrl;
    }

    public void setBaseWebUrl(String baseWebUrl) {
        this.baseWebUrl = baseWebUrl;
    }
}

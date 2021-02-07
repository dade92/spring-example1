package com.myprojects;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("test")
public class TestConfiguration {
    public String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

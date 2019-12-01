package com.tang.leyou.register.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Classname ZuulFilterProperties
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/12/1 21:28
 * @Created by ASUS
 */
@ConfigurationProperties(value = "leyou.filter")
public class ZuulFilterProperties {

    private List<String> allowPaths;

    public List<String> getAllowPaths() {
        return allowPaths;
    }

    public void setAllowPaths(List<String> allowPaths) {
        this.allowPaths = allowPaths;
    }
}
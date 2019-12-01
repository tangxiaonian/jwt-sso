package com.tang.leyou.user.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Classname EmailProperties
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/11/27 18:38
 * @Created by ASUS
 */
@ConfigurationProperties(prefix = "amqp.email")
public class EmailProperties {

    private String exchange;

    private String emailKey;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getEmailKey() {
        return emailKey;
    }

    public void setEmailKey(String emailKey) {
        this.emailKey = emailKey;
    }
}
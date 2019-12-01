package com.tang.leyou.register.config;

import com.tang.leyou.auth.common.RsaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @Classname JwtProperties
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/11/29 22:36
 * @Created by ASUS
 */
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    // 公钥

    private String pubKeyPath;

    // cookieName

    private String cookieName;

    // 公钥

    private PublicKey publicKey;


    private static final Logger logger = LoggerFactory.getLogger(JwtProperties.class);

    @PostConstruct
    public void init() throws Exception {

        this.publicKey = RsaUtils.getPublicKey("C:\\TEMP\\rsa\\rsa.pub");

        logger.info("读取公钥私钥文件...");

    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getPubKeyPath() {
        return pubKeyPath;
    }

    public void setPubKeyPath(String pubKeyPath) {
        this.pubKeyPath = pubKeyPath;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }



}
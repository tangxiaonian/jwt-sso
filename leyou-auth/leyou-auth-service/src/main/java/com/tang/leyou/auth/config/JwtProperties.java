package com.tang.leyou.auth.config;

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

    // 密钥

    private String secret;

    // 公钥

    private String pubKeyPath;

    // 私钥

    private String priKeyPath;

    // token过期时间

    private int expire;

    // cookieName

    private String cookieName;

    // 公钥

    private PublicKey publicKey;

    // 私钥

    private PrivateKey privateKey;

    private static final Logger logger = LoggerFactory.getLogger(JwtProperties.class);

    @PostConstruct
    public void init() throws Exception {

        this.privateKey = RsaUtils.getPrivateKey("C:\\TEMP\\rsa\\rsa.pri");

        this.publicKey = RsaUtils.getPublicKey("C:\\TEMP\\rsa\\rsa.pub");

        logger.info("读取公钥私钥文件...");

    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPubKeyPath() {
        return pubKeyPath;
    }

    public void setPubKeyPath(String pubKeyPath) {
        this.pubKeyPath = pubKeyPath;
    }

    public String getPriKeyPath() {
        return priKeyPath;
    }

    public void setPriKeyPath(String priKeyPath) {
        this.priKeyPath = priKeyPath;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

}
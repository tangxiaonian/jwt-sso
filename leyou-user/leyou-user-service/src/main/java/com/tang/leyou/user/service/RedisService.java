package com.tang.leyou.user.service;

/**
 * @Classname RedisService
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/11/26 20:12
 * @Created by ASUS
 */
public interface RedisService {

    public Long checkKey(String key);

    public void saveKey(String key, String verificationCode);

    public Object getKey(String key);


}
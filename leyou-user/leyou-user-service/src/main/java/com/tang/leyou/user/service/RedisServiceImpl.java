package com.tang.leyou.user.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Classname RedisServiceImpl
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/11/26 20:12
 * @Created by ASUS
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Long checkKey(String key) {

        Object qqValue = redisTemplate.opsForValue().get(key);

        // key存在 有效期还未失效
        if (qqValue != null) {

            return redisTemplate.getExpire(key,TimeUnit.SECONDS);

        }else {

            return 0L;

        }

    }

    @Override
    public void saveKey(String qq, String verificationCode) {
        redisTemplate.opsForValue().set(qq,verificationCode, 120, TimeUnit.SECONDS);
    }

    @Override
    public Object getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
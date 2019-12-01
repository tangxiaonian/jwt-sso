package com.tang.leyou.auth.service;

import com.tang.leyou.auth.common.JwtHelper;
import com.tang.leyou.auth.config.JwtProperties;
import com.tang.leyou.user.domain.TbUser;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname AuthServiceImpl
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/11/29 22:38
 * @Created by ASUS
 */
@EnableConfigurationProperties(value = JwtProperties.class)
@Service
public class AuthServiceImpl implements AuthService{

    @Resource
    private JwtProperties jwtProperties;

    @Override
    public String createToken(TbUser tbUser) {

        Map<String, Object> map = new HashMap<>();

        map.put("id", tbUser.getId());

        map.put("username", tbUser.getUsername());

        return JwtHelper.createJwt(map, 1800, jwtProperties.getPrivateKey());
    }
}
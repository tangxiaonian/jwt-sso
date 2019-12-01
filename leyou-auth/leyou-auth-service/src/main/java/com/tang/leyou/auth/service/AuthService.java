package com.tang.leyou.auth.service;

import com.tang.leyou.user.domain.TbUser;

/**
 * @Classname AuthService
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/11/29 22:37
 * @Created by ASUS
 */
public interface AuthService {

    public String createToken(TbUser tbUser);

}
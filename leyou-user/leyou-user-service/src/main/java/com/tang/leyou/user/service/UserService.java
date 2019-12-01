package com.tang.leyou.user.service;

import com.tang.leyou.user.domain.TbUser;

/**
 * @Classname UserService
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/11/27 19:38
 * @Created by ASUS
 */
public interface UserService {

    public TbUser addUser(TbUser tbUser);

    TbUser checkLogin(String username, String password);
}

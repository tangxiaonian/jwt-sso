package com.tang.leyou.auth.controller;

import com.tang.leyou.auth.common.CookieUtils;
import com.tang.leyou.auth.common.JwtHelper;
import com.tang.leyou.auth.common.UserInfo;
import com.tang.leyou.auth.config.JwtProperties;
import com.tang.leyou.auth.service.AuthService;
import com.tang.leyou.common.ResponseResult;
import com.tang.leyou.user.api.UserFeign;
import com.tang.leyou.user.domain.TbUser;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname AuthController
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/11/29 21:25
 * @Created by ASUS
 */
@EnableConfigurationProperties(value = JwtProperties.class)
@RestController
public class AuthController {

    @Resource
    private UserFeign userFeign;

    @Resource
    private AuthService authService;

    @Resource
    private JwtProperties jwtProperties;

    /**
     * MethodName authentication
     * Description [ 登录授权 生成token]
     * Date 2019/11/29 21:28
     * Param [username, password]
     * return
     **/
    @PostMapping("/login")
    public ResponseResult<String> authorizationLogin(@RequestParam(value = "username") String username,
                                                     @RequestParam(value = "password") String password,
                                                     HttpServletRequest request, HttpServletResponse response) {

        ResponseResult<TbUser> tbUserResponseResult = userFeign.userLogin(username, password);

        if (tbUserResponseResult != null) {

            System.out.println("生成token...");

            String token = authService.createToken(tbUserResponseResult.getData());

            CookieUtils.setCookie(request, response, jwtProperties.getCookieName(), token, 3600);

            return new ResponseResult<String>("登录成功!token生成成功!", 200, "success!");

        }
        return new ResponseResult<String>("登录失败!", 400, "fail!");
    }

    /**
     * MethodName verify
     * Description [ 验证 token 的有效性,刷新token ]
     * Date 2019/12/1 20:31
     * Param [token]
     * return
     **/
    @GetMapping("/verify")
    public ResponseResult<UserInfo> verify(@CookieValue(name = "leyou") String token,
                                           HttpServletRequest request, HttpServletResponse response) {

        UserInfo userInfo = JwtHelper.getInfoFromToken(token, jwtProperties.getPublicKey());

        if (userInfo != null) {

            TbUser tbUser = new TbUser();

            tbUser.setId(userInfo.getId());

            tbUser.setUsername(userInfo.getUsername());

            // 刷新 token
            authService.createToken(tbUser);

            CookieUtils.setCookie(request, response, jwtProperties.getCookieName(), token, 3600);

            return new ResponseResult<UserInfo>("验证成功!",200,userInfo);
        }
        return new ResponseResult<UserInfo>("验证失败!",400,null);
    }


}
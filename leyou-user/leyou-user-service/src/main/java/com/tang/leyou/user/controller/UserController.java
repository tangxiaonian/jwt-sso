package com.tang.leyou.user.controller;

import com.tang.leyou.common.ResponseResult;
import com.tang.leyou.user.domain.EmailProperties;
import com.tang.leyou.user.domain.TbUser;
import com.tang.leyou.user.service.RedisService;
import com.tang.leyou.user.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Classname UserController
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/11/26 20:31
 * @Created by ASUS
 */
@EnableConfigurationProperties(value = {EmailProperties.class})
@RestController
public class UserController {

    @Resource
    private RedisService redisService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private EmailProperties emailProperties;

    @Resource
    private UserService userService;

    /**
     * 发送邮箱服务
     * @param qq
     * @return
     */
    @GetMapping("/code")
    public ResponseResult sendVerifyCode(@RequestParam(name = "qq") String qq){

        // 检查 key 是否有效  失效
        Long expireTime = redisService.checkKey("user:qq:" + qq);

        // 没有此 key 或者 key 过期了
        if (expireTime == 0L) {

            String verificationCode = UUID.randomUUID().toString();

            redisService.saveKey("user:qq:"+qq,verificationCode);

            // 通过消息 发送邮件
            Map<String, String> map = new HashMap<>();

            map.put("qq", qq);
            map.put("subject", "验证邮件");
            map.put("verificationCode", verificationCode);

            rabbitTemplate.convertSendAndReceive(emailProperties.getExchange(),
                    emailProperties.getEmailKey(), map);

            System.out.println( "发送邮件..." );

            return new ResponseResult<String>("success",200,null);
        }else {
            return new ResponseResult<String>(String.format("有效期还未失效!请等待 %s S",expireTime),200,null);
        }

    }
    /**
     * MethodName userRegister
     * Description [ 用户注册 ]
     * Date 2019/11/27 22:22
     * Param [tbUser, verificationCode]
     * return
     **/
    @PostMapping("/register")
    public ResponseResult<TbUser> userRegister(@RequestBody TbUser tbUser,
                                       @RequestParam(value = "verificationCode",defaultValue = "") String verificationCode) {
        // 检查验证码的正确性
        Object code = redisService.getKey("user:qq:" + tbUser.getQq());

        if (code != null) {

            if (!"".equals(verificationCode) && verificationCode.equals(code.toString())) {
                // 注册用户
                tbUser = userService.addUser(tbUser);

                if (tbUser != null) {
                    return new ResponseResult<TbUser>("注册成功!", 200, tbUser);
                }
            }
        }

        return new ResponseResult<TbUser>("注册失败!验证码可能不正确!", 400, new TbUser());
    }


    @PostMapping("/login")
    public ResponseResult<TbUser> userLogin(@RequestParam(value = "username") String username,
                                            @RequestParam(value = "password")String password) {

        TbUser tbUser = userService.checkLogin(username,password);

        if (tbUser == null) {
            return new ResponseResult<TbUser>("登录失败！",400,null);
        }
        return new ResponseResult<TbUser>("登录成功！",200,tbUser);
    }

}
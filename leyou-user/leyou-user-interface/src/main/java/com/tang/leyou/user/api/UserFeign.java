package com.tang.leyou.user.api;

import com.tang.leyou.common.ResponseResult;
import com.tang.leyou.user.domain.TbUser;
import com.tang.leyou.user.fallback.UserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Classname UserFegin
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/11/29 21:18
 * @Created by ASUS
 */
@FeignClient(value = "LEYOU-USER-SERVICE",fallbackFactory = UserFallbackFactory.class)
public interface UserFeign {

    @PostMapping("/login")
    public ResponseResult<TbUser> userLogin(@RequestParam(value = "username") String username,
                                            @RequestParam(value = "password") String password);

}

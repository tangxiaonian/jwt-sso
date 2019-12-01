package com.tang.leyou.user.fallback;

import com.tang.leyou.common.ResponseResult;
import com.tang.leyou.user.domain.TbUser;
import com.tang.leyou.user.api.UserFeign;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname UserFallbackFactory
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/11/29 21:23
 * @Created by ASUS
 */
@Component
public class UserFallbackFactory implements FallbackFactory<UserFeign> {

    @Override
    public UserFeign create(Throwable throwable) {

        return new UserFeign() {

            @Override
            public ResponseResult<TbUser> userLogin(String username, String password) {

                System.out.println( "熔断降级了...." );

                return null;
            }
        };
    }
}
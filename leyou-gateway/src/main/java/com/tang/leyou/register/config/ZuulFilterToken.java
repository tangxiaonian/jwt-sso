package com.tang.leyou.register.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.tang.leyou.auth.common.CookieUtils;
import com.tang.leyou.auth.common.JwtHelper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Classname ZuulFilterToken
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/12/1 21:25
 * @Created by ASUS
 */
@Component
@EnableConfigurationProperties(value = {ZuulFilterProperties.class,JwtProperties.class})
public class ZuulFilterToken extends ZuulFilter {

    @Resource
    private ZuulFilterProperties zuulFilterProperties;

    @Resource
    private JwtProperties jwtProperties;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {

        return 10;
    }

    @Override
    public boolean shouldFilter() {

        RequestContext requestContext = RequestContext.getCurrentContext();

        HttpServletRequest request = requestContext.getRequest();

        // 获取路径
        String uri = request.getRequestURI();

        // 匹配白名单
        return zuulFilterProperties.getAllowPaths().stream().anyMatch((item)-> uri.indexOf(item) > 0 );
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();

        HttpServletRequest request = requestContext.getRequest();

        // 获取 cookie
        String leyou = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());

        try {

            // 解析 token
            JwtHelper.parseJwt(leyou,jwtProperties.getPublicKey());

        } catch (Exception e) {

            // 解析 异常  停止继续转发
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            requestContext.setResponseBody("token is null!");

            e.printStackTrace();
        }

        return null;
    }
}
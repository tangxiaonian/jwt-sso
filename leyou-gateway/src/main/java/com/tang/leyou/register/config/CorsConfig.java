package com.tang.leyou.register.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Classname CorsConfig
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/11/16 21:55
 * @Created by ASUS
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 允许发送cookie
        corsConfiguration.setAllowCredentials(true);

        corsConfiguration.addAllowedOrigin("http://manage.leyou.com");

        corsConfiguration.addAllowedOrigin("http://www.leyou.com");

        corsConfiguration.addAllowedMethod("*");

        corsConfiguration.addAllowedHeader("*");

        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
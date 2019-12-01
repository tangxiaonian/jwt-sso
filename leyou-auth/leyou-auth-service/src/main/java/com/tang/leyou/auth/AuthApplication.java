package com.tang.leyou.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Classname com.tang.leyou.auth.AuthApplication
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/11/29 21:37
 * @Created by ASUS
 */
@SpringBootApplication(scanBasePackages = {"com.tang.leyou"})
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.tang.leyou.user"})
@EnableCircuitBreaker
public class AuthApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthApplication.class, args);

    }

}
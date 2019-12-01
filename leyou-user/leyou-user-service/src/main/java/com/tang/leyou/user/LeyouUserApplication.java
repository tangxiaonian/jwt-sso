package com.tang.leyou.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Classname LeyouUserApplication
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/11/25 22:49
 * @Created by ASUS
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.tang.leyou.user.mapper"})
public class LeyouUserApplication {

    public static void main(String[] args) {

        SpringApplication.run(LeyouUserApplication.class,args);

    }

}
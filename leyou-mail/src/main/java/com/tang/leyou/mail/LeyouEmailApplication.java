package com.tang.leyou.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Classname LeyouUserApplication
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/11/25 22:49
 * @Created by ASUS
 */
@EnableEurekaClient
@SpringBootApplication
public class LeyouEmailApplication {

    public static void main(String[] args) {

        SpringApplication.run(LeyouEmailApplication.class,args);

    }

}
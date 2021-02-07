package com.guli.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-30 22:29
 */
@EnableEurekaClient
@SpringBootApplication
@ComponentScan(basePackages={"com.guli.ucenter","com.guli.common"})
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}

package com.music.demo.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan(basePackages = "com.music")
@SpringBootApplication
@EnableDiscoveryClient
//@MapperScan("com.music.demo.admin.mapper")
public class UserApp {
    public static void main(String[] args) {
        SpringApplication.run(UserApp.class);
    }
}

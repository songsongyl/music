package com.music.demo.message;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.music")
@MapperScan({"com.music.demo.message.mapper","com.music.demo.admin.mapper"})
public class MessageApp {
    public static void main(String[] args) {
        SpringApplication.run(MessageApp.class);
    }
}

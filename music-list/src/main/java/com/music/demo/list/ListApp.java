package com.music.demo.list;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.music")
@MapperScan("com.music.demo.list.mapper")
public class ListApp {
    public static void main(String[] args) {
        SpringApplication.run(ListApp.class, args);
    }
}

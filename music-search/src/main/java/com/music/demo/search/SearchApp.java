package com.music.demo.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.music")
@MapperScan({"com.music.demo.music.mapper","com.music.demo.user.mapper","com.music.demo.admin.mapper"})
public class SearchApp {
    public static void main(String[] args) {
        SpringApplication.run(SearchApp.class);
    }
}

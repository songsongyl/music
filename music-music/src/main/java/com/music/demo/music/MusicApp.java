package com.music.demo.music;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.music")
public class MusicApp {
    public static void main(String[] args) {
        SpringApplication.run(MusicApp.class, args);
    }
}

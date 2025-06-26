package com.music.demo.gateway;

import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(
        exclude =  {
                DataSourceAutoConfiguration.class,
                DruidDataSourceAutoConfigure.class
        }
)
@EnableDiscoveryClient
public class GatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class);
    }
}

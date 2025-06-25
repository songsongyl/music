package com.music.demo.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.music")
public class LoginApp {
    public static void main(String[] args) {
        SpringApplication.run(LoginApp.class);
    }
}

package com.music.demo.login.service.impl;

import com.music.demo.domain.entity.User;
import com.music.demo.login.service.IRegistryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ILoginServiceImplTest {

    @Autowired
    private IRegistryService iRegistryService;

    @Test
    void Registry() {
        User user = User.builder()
                .username("zhang")
                .password("12345")
                .name("张三")
                .phone("12345612345")
                .avatar("jsdfj")
                .email("2732873@jsa.com")
                .build();
        iRegistryService.registry(user);
    }
}
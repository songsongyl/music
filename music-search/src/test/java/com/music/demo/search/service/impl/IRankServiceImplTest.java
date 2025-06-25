package com.music.demo.search.service.impl;

import com.music.demo.domain.entity.Music;
import com.music.demo.search.service.IRankService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class IRankServiceImplTest {

    @Autowired
    IRankService iRankService;

    @Test
    void rank() {
        List<Music> musicList = iRankService.rankMusic();
        musicList.forEach(music -> {
            log.debug("{}",music.toString());
        });
    }

}
package com.music.demo.search.service.impl;

import com.music.demo.domain.entity.Music;
import com.music.demo.search.service.ISearchService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@SpringBootTest
@Slf4j
class SearchServiceImplTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ISearchService searchService;

    @Test
    void segmentator() {
        searchService.segmentator();
    }

    @Test
    void searchMusic() {
        List<Music> musicList = searchService.searchMusic("李四");
        musicList.forEach(
                music -> {
                    log.debug(music.toString());
                }
        );
    }
    
}
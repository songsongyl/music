package com.music.demo.list.service.impl;

import com.music.demo.domain.entity.Music;
import com.music.demo.domain.entity.MusicList;
import com.music.demo.list.service.ListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ListServiceImplTest {

    @Autowired
    private ListService listService;
    @Test
    void add() {
        MusicList musicList = new MusicList();
        musicList.setListName("我的收藏");
        musicList.setListCollectCount(1);
        musicList.setUserId("1"); // 你需要设置一个有效的用户 ID

        List<String> musicIds = new ArrayList<>();
        musicIds.add("1");
        musicIds.add("2");
        musicList.setMusicIds(musicIds);

        listService.add(musicList);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findAll() {
    }
}
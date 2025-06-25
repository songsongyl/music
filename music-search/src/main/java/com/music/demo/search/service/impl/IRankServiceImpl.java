package com.music.demo.search.service.impl;

import com.music.demo.domain.entity.Music;
import com.music.demo.music.mapper.MusicMapper;
import com.music.demo.search.service.IRankService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class IRankServiceImpl implements IRankService {

    private final MusicMapper mapper;
    private final StringRedisTemplate stringRedisTemplate;


    @Override
    public List<Music> rankMusic() {

        List<Music> musicList = mapper.selectList(null);

        for (Music music : musicList) {
            stringRedisTemplate.opsForZSet().add("rank",music.getId(),music.getPlayCount());
        }
//        stringRedisTemplate.expire("rank",7, TimeUnit.DAYS);
        Set<String> musicIdList =  stringRedisTemplate.opsForZSet().reverseRange("rank",0,2);
        List<Music> musicList1 = new ArrayList<>();
        for (String musicId : musicIdList) {
             musicList1.add(mapper.selectById(musicId));
        }


        return musicList1;
    }
}

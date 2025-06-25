package com.music.demo.search.service.impl;

import com.music.demo.domain.entity.Music;
import com.music.demo.music.mapper.MusicMapper;
import com.music.demo.search.service.ISearchService;
import com.music.demo.search.util.WordsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ISearchServiceImpl implements ISearchService {

    private final StringRedisTemplate stringRedisTemplate;
    private final MusicMapper mapper;

    @Override
    public List<Music> segmentator() {
        List<Music> musicList = mapper.selectList(null);
//        List<String> keyWordList =

        for (Music music : musicList) {
            //歌手的名字，直接放入redis
            stringRedisTemplate.opsForSet().add(music.getAuthor(),music.getId());

            String words = music.getAuthor() + music.getLyrics() + music.getDescription();
            List<String> keyWordList = WordsUtil.getInstance().word(words);

            for (String keyWord : keyWordList) {
                stringRedisTemplate.opsForSet().add(keyWord, music.getId());
            }

        }



        return List.of();
    }

    @Override
    public List<Music> searchMusic(String keyword) {
        List<Music> musicList = new ArrayList<>();

        Set<String> musicIdSet = stringRedisTemplate.opsForSet().members(keyword);

        for (String musicId : musicIdSet) {
            musicList.add(mapper.selectById(musicId)) ;
        }

        return musicList;
    }
}

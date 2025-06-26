package com.music.demo.search.service.impl;

import com.music.demo.domain.entity.Music;
import com.music.demo.domain.entity.MusicList;
import com.music.demo.domain.entity.User;
import com.music.demo.list.mapper.ListMapper;
import com.music.demo.music.mapper.MusicMapper;
import com.music.demo.music.service.MusicService;
import com.music.demo.search.service.IRankService;
import com.music.demo.user.mapper.UserMapper;
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
    private final MusicService musicService;
    private final StringRedisTemplate stringRedisTemplate;
    private final ListMapper listMapper;
    private final UserMapper userMapper;

    @Override
    public List<Music> rankMusic() {

        List<Music> musicList = mapper.selectList(null);

        for (Music music : musicList) {
            stringRedisTemplate.opsForZSet().add("MusicRank",music.getId(),music.getPlayCount());
        }
//        stringRedisTemplate.expire("rank",7, TimeUnit.DAYS);
        Set<String> musicIdList =  stringRedisTemplate.opsForZSet().reverseRange("MusicRank",0,2);
        List<Music> musicList1 = new ArrayList<>();
        for (String musicId : musicIdList) {
             musicList1.add(mapper.selectById(musicId));
        }


        return musicList1;
    }

    @Override
    public List<MusicList> rankMusicList() {
        List<MusicList> musicList = listMapper.selectList(null);

        for (MusicList musiclist : musicList) {
            stringRedisTemplate.opsForZSet().add("ListRank",musiclist.getId(),musiclist.getListCount());
        }
//        stringRedisTemplate.expire("rank",7, TimeUnit.DAYS);
        Set<String> musicIdList =  stringRedisTemplate.opsForZSet().reverseRange("ListRank",0,2);
        List<MusicList> musicList1 = new ArrayList<>();
        for (String musicId : musicIdList) {
            musicList1.add(listMapper.selectById(musicId));
        }
        return musicList1;
    }

    @Override
    public List<User> rankSinger() {
        List<User> userList = userMapper.selectList(null);

        for (User user : userList) {
            if( user.getRole().equals(User.SINGER)){
                List<Music> list = musicService.findAllBySingerId(user.getId());
                long songCount = list != null ? list.size() : 0;
                stringRedisTemplate.opsForZSet().add("SingerRank",user.getId(),songCount);

            }
        }
//        stringRedisTemplate.expire("rank",7, TimeUnit.DAYS);
        Set<String> userIdList =  stringRedisTemplate.opsForZSet().reverseRange("SingerRank",0,2);
        List<User> userList1 = new ArrayList<>();
        for (String userId : userIdList) {
            userList1.add(userMapper.selectById(userId));
        }
        return userList1;
    }
}

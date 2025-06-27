package com.music.demo.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.music.demo.common.exception.music.MusicException;
import com.music.demo.common.exception.user.UserCredentialsException;
import com.music.demo.common.exception.user.UsernameEmptyException;
import com.music.demo.common.util.ULID;
import com.music.demo.domain.entity.Music;
import com.music.demo.music.mapper.MusicMapper;
import com.music.demo.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MusicMapper musicMapper;

    private final ULID ulid;

    @Override
    public void add(Music music) {
        music.setId(ulid.nextULID());
        if(music.getTitle() == null || music.getTitle().equals("")){
            throw new MusicException("音乐标题为空");
        }
        if(music.getAuthor() == null || music.getAuthor().equals("")){
            throw new MusicException("音乐作者为空");
        }
        if(music.getDuration() == null || music.getDuration().equals("")){
            throw new MusicException("音乐时长为空");
        }

        musicMapper.insert(music);
    }

    @Override
    public void delete(String mid) {
        musicMapper.deleteById(mid);
    }

    @Override
    public void update(Music music) {
        musicMapper.updateById(music);
    }

    @Override
    public List<Music> findAll() {
        return musicMapper.selectList(null);
    }

    @Override
    public Music findById(String  id) {
        Music music = musicMapper.selectById(id);
        if (music == null) {
            throw new MusicException("该歌曲不存在");
        }
        return music;
    }

    @Override
    public List<Music> findCollectMusics(String id) {
        // 生成 Redis Key
        String redisKey =  "userId" + id;
        // 从 Redis 获取音乐 ID 集合
        Set<String> musicIds = stringRedisTemplate.opsForSet().members(redisKey);

        if (musicIds == null || musicIds.isEmpty()) {
            return Collections.emptyList(); // 返回空列表
        }
        // 根据音乐 ID 查询音乐对象
        QueryWrapper<Music> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("music_id", musicIds);

        return musicMapper.selectList(queryWrapper);
    }

    @Override
    public List<Music> findAllBySingerId(String singerId) {
        List<Music> musicList = musicMapper.selectList(null);
      return   musicList.stream().filter(music -> music.getAuthor().equals(singerId)).collect(Collectors.toList());
    }
}

package com.music.demo.list.service.impl;

import com.music.demo.common.exception.music.MusicException;
import com.music.demo.common.util.ULID;
import com.music.demo.domain.entity.MusicList;

import com.music.demo.list.mapper.ListMapper;
import com.music.demo.list.service.ListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class ListServiceImpl implements ListService {

    private final ListMapper listMapper;
    private final ULID ulid;

    @Override
    public void add(MusicList list) {
        list.setId(ulid.nextULID());
        listMapper.insert(list);
    }

    @Override
    public void update(MusicList list) {
        listMapper.updateById(list);
    }

    @Override
    public void delete(String id) {
        listMapper.deleteById(id);
    }

    @Override
    public List<MusicList> findAll() {
        return listMapper.selectList(null);
    }

    @Override
    public MusicList findById(String id) {
        MusicList musicList = listMapper.selectById(id);
        if (musicList == null) {
            throw new MusicException("该歌曲不存在");
        }
        return musicList;
    }

    @Override
    public List<MusicList> findCollectList(String id) {
        log.debug("查询用户ID为{}的收藏歌单", id);
        if (id == null) {
            throw new MusicException("用户ID不能为空");
        }
        List<MusicList> list = new ArrayList<MusicList>();
        List<MusicList> musicLists = listMapper.selectList(null);
        return musicLists.stream()
                .filter(musicList -> id.equals(musicList.getUserId()))
                .filter(musicList -> musicList.getListStatus() == 1)
                .collect(Collectors.toList());

    }

    @Override
    public List<MusicList> findByUserId(String userId) {
        List<MusicList> musicLists = listMapper.selectList(null);
      return   musicLists.stream().filter(musicList -> userId.equals(musicList.getUserId())).collect(Collectors.toList());
    }
}

package com.music.demo.list.service;


import com.music.demo.domain.entity.MusicList;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface ListService {
//    @Insert("INSERT INTO music_list (list_name, list_count, list_collect_count, user_id, list_musics) " +
//            "VALUES (#{listName}, #{listCount}, #{listCollectCount}, #{userId}, #{listMusicsJson})")
    void add(MusicList list);
    void update(MusicList list);
    void delete(String id);
    List<MusicList> findAll();
    MusicList findById(String id);
    List<MusicList> findCollectList(String id);
    List<MusicList> findByUserId(String userId);
}

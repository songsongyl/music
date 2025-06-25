package com.music.demo.music.service;

import com.music.demo.domain.entity.Music;

import java.util.List;

public interface MusicService {
    void add(Music music);
    void delete(String mid);
    void update(Music music);
    List<Music> findAll();
    Music findById(String id);
    List<Music> findCollectMusics(String id);
}

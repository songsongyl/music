package com.music.demo.search.service;

import com.music.demo.domain.entity.Music;

import java.util.List;

public interface ISearchService {
    List<Music> segmentator();
    List<Music> searchMusic(String keyword);
}

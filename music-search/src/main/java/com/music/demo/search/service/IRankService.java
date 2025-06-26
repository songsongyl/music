package com.music.demo.search.service;

import com.music.demo.domain.entity.Music;
import com.music.demo.domain.entity.MusicList;
import com.music.demo.domain.entity.User;

import java.util.List;

public interface IRankService {
    List<Music> rankMusic();
    List<MusicList> rankMusicList();
    List<User> rankSinger();
}

package com.music.demo.upload.service;

import com.music.demo.common.exception.music.MusicException;
import com.music.demo.domain.entity.Music;
import com.music.demo.music.service.MusicService;
import com.music.demo.music.service.impl.MusicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadService {

    @Autowired
    private MusicService musicService;

    public void saveLyric(String lyric,String musicId) {
       Music music =  musicService.findById(musicId);
       if(music==null) {
           throw new MusicException("歌曲不存在");
       }
        music.setLyrics(lyric);
        musicService.update( music);
    }
}

package com.music.demo.music.controller;

import com.music.demo.common.result.HttpResult;
import com.music.demo.domain.entity.Music;
import com.music.demo.music.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/music")
@Tag(name = "music模块")
public class MusicController {
    @Autowired
    private MusicService musicService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Operation(summary = "添加歌曲")
    @PostMapping("/addMusic")
    public HttpResult<String> addList(@RequestBody Music music) {
        musicService.add(music);
        return HttpResult.success(music.getTitle()+"添加成功");
    }

    @DeleteMapping("/deleteMusic/{mid}")
    @Operation(summary = "删除歌曲")
    public HttpResult<String> deleteMusic(@PathVariable String mid) {
        musicService.delete(mid);
        return HttpResult.success("删除成功");
    }

    @GetMapping("/findAll")
    @Operation(summary = "查找所有歌曲")
    public HttpResult<List<Music>> findAllMusics() {
        List<Music> list = musicService.findAll();
        return HttpResult.success(list);
    }

    @PutMapping("/updateMusic")
    @Operation(summary = "更新歌曲")
    public HttpResult<String> updateList(@RequestBody Music music) {
        musicService.update(music);
        return HttpResult.success(music.getTitle()+ "更新成功");
    }

    @PutMapping("/collect/{mid}")
    @Operation(summary = "收藏歌曲")
    public HttpResult<String> collectList(@PathVariable String mid,@RequestHeader("uid") String uid) {
        Music music = musicService.findById(mid);
        Integer status = music.getStatus();
        String redisKey =  "userId" + uid;
        if(status == 1) {
            music.setStatus(0);
            if(music.getCollectCount() >0) music.setCollectCount(music.getCollectCount()-1);
            log.debug(music.getStatus().toString());
            musicService.update(music);
            // 从 Redis 中移除音乐 ID
            stringRedisTemplate.opsForSet().remove(redisKey, mid.toString());
            return HttpResult.success("取消收藏");
        }
        else {
            music.setStatus(1);
            music.setCollectCount(music.getCollectCount()+1);
            musicService.update(music);
            // 将音乐 ID 添加到 Redis 的 Set 中
            stringRedisTemplate.opsForSet().add(redisKey, mid.toString());
            return HttpResult.success("收藏成功");
        }
    }

    @GetMapping("/collectMusics")
    @Operation(summary = "查找所有收藏歌曲")
    public HttpResult<List<Music>> collectMusics(@RequestHeader("uid") String uid) {
        return HttpResult.success(musicService.findCollectMusics(uid));
    }
}

package com.music.demo.search.controller;

import com.music.demo.common.result.HttpResult;
import com.music.demo.domain.entity.Music;
import com.music.demo.domain.entity.MusicList;
import com.music.demo.domain.entity.User;
import com.music.demo.search.service.IRankService;
import com.music.demo.search.service.impl.IRankServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "榜单接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rank")
public class RankController {

    private final IRankService iRankService;

    @Operation(summary = "根据歌曲点击量排名的榜单")
    @GetMapping("/music")
    public HttpResult<List<Music>> rankMusic() {
        return HttpResult.success(iRankService.rankMusic());
    }

    @Operation(summary = "根据歌单点击量排名的榜单")
    @GetMapping("/list")
    public HttpResult<List<MusicList>> rankMusicList() {
        return HttpResult.success(iRankService.rankMusicList());
    }

    @Operation(summary = "根据歌手的歌曲数量排名的歌手榜单")
    @GetMapping("/singer")
    public HttpResult<List<User>> rankSingerList() {
        return HttpResult.success(iRankService.rankSinger());
    }
}

package com.music.demo.list.controller;

import com.music.demo.common.result.HttpResult;
import com.music.demo.domain.entity.MusicList;
import com.music.demo.list.service.ListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@Tag(name = "歌单")
@RestController
@RequestMapping("/api/list")
public class ListController {
    @Autowired
    private ListService listService;

    @Operation(summary = "添加歌单")
    @PostMapping("/addlist")
    public HttpResult<String> addList(@RequestBody MusicList list) {
        listService.add(list);
        return HttpResult.success(list.getListName()+"添加成功");
    }

    @DeleteMapping("/deletelist/{lid}")
    @Operation(summary = "删除歌单")
    public HttpResult<String> deleteList(@PathVariable String lid) {
        listService.delete(lid);
        return HttpResult.success("删除成功");
    }

    @GetMapping("/findAll")
    @Operation(summary = "查找所有歌单")
    public HttpResult<List<MusicList>> findAllList() {
        List<MusicList> list = listService.findAll();
        return HttpResult.success(list);
    }

    @Operation(summary = "查找某一用户的所有歌单")
    @GetMapping("/findALlByUId")
    public HttpResult<List<MusicList>> findAllByUId(@RequestHeader("uid") String uid){
       return HttpResult.success( listService.findByUserId(uid));
    }


    @PutMapping("/update")
    @Operation(summary = "更新歌单")
    public HttpResult<String> updateList(@RequestBody MusicList list) {
        listService.update(list);
        return HttpResult.success(list.getListName()+ "更新成功");
    }

    @PutMapping("/collect/{lid}")
    @Operation(summary = "收藏歌单")
    public HttpResult<String> collectList(@PathVariable String lid,@RequestHeader("uid") String uid) {
//        List<MusicList> lists = listService.findByUserId(uid);
        MusicList musicList = listService.findById(lid);

        Integer status = musicList.getListStatus();
        if(status == 1) {
            musicList.setListStatus(0);
            if(musicList.getListCollectCount() >0) musicList.setListCollectCount(musicList.getListCollectCount()-1);
            log.debug(musicList.getListStatus().toString());
            listService.update(musicList);
            return HttpResult.success("取消收藏");
        }
        else {
            musicList.setListStatus(1);
            musicList.setListCollectCount(musicList.getListCollectCount()+1);
            listService.update(musicList);
            return HttpResult.success("收藏成功");
        }
    }

    @GetMapping("/collectlists")
    @Operation(summary = "查找所有收藏歌单")
    public HttpResult<List<MusicList>> collectLists(@RequestHeader("uid") String uid) {
        log.debug("查询用户ID为{}的收藏歌单", uid);
        return HttpResult.success(listService.findCollectList(uid));
    }

}

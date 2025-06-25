package com.music.demo.search.controller;

import com.alibaba.nacos.api.naming.pojo.healthcheck.impl.Http;
import com.music.demo.common.result.HttpResult;
import com.music.demo.domain.entity.Music;
import com.music.demo.search.service.ISearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "搜索接口")
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final ISearchService iSearchService;

    @Operation(summary = "测试关键词搜索")
    @GetMapping("/search")
    public HttpResult<List<Music>> searchMusic(String keywords) {
        return HttpResult.success(iSearchService.searchMusic(keywords));
    }

}

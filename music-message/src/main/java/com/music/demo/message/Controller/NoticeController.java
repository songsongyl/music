package com.music.demo.message.Controller;

import com.music.demo.domain.entity.Notice;
import com.music.demo.common.result.HttpResult;
import com.music.demo.message.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/notice")
@Tag(name = "notice模块")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @Operation(summary = "添加公告")
    @PostMapping("/addNotice")
    public HttpResult<String> addList(@RequestBody Notice notice) {
        noticeService.add(notice);
        return HttpResult.success(notice.getTitle()+"添加成功");
    }

    @DeleteMapping("/deleteNotice/{nid}")
    @Operation(summary = "删除公告")
    public HttpResult<String> deleteMusic(@PathVariable String nid) {
        noticeService.delete(nid);
        return HttpResult.success("删除成功");
    }

    @GetMapping("/findAll")
    @Operation(summary = "查找所有公告")
    public HttpResult<List<Notice>> findAllMusics() {
        List<Notice> list = noticeService.findAll();
        return HttpResult.success(list);
    }
    @Operation(summary = "查询公告")
    @GetMapping("/searchNoticeById")
    public HttpResult<Notice> getNotice(@RequestParam String nId){
        Notice notice = noticeService.findById(nId);
        return HttpResult.success(notice);
    }
    @Operation(summary = "根据关键字查询公告")
    @GetMapping("/searchNoticeByCondition")
    public HttpResult<List<Notice>> searchNotices(String keyword) {
        List<Notice> notices = noticeService.findByKeyword(keyword);
        return HttpResult.success(notices);
    }

    @PutMapping("/updateNotice")
    @Operation(summary = "更新公告")
    public HttpResult<String> updateList(@RequestBody Notice notice) {
        noticeService.update(notice);
        return HttpResult.success(notice.getTitle()+ "更新成功");
    }
}

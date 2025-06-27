package com.music.demo.message.service;

import com.music.demo.domain.entity.Notice;

import java.util.List;

public interface NoticeService {
    void add(Notice notice);
    void delete(String nid);
    void update(Notice notice);
    List<Notice> findAll();
    Notice findById(String nid);
    List<Notice> findByKeyword(String keyword);
    //List<Notice> findByCondition(String keyword);
}

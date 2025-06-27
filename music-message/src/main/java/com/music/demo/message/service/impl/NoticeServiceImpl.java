package com.music.demo.message.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.music.demo.common.exception.message.MessageException;
import com.music.demo.common.util.ULID;
import com.music.demo.domain.entity.Notice;
import com.music.demo.message.mapper.NoticeMapper;
import com.music.demo.message.service.NoticeService;
import com.music.demo.search.util.WordsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private NoticeMapper mapper;

    @Autowired
    private ULID ulid;

    @Override
    public void add(Notice notice) {
        notice.setId(ulid.nextULID());
        mapper.insert(notice);
    }

    @Override
    public void delete(String nid) {
        mapper.deleteById(nid);
    }

    @Override
    public void update(Notice notice) {
        mapper.updateById(notice);
    }

    @Override
    public List<Notice> findAll() {
        LambdaQueryWrapper<Notice> queryWrapper =
                new LambdaQueryWrapper<>();
        return mapper.selectList(queryWrapper);
    }

    @Override
    public Notice findById(String nid) {
        Notice notice = mapper.selectById(nid);
        if (notice == null) {
            throw new MessageException("该公告不存在");
        }
        return notice;
    }

    @Override
    public List<Notice> findByKeyword(String keyword) {
        List<String> keywords = WordsUtil.getInstance().word(keyword);
        Set<Notice> sets = new HashSet<>();
        for(String key:keywords){
            Set<String> ids = stringRedisTemplate.opsForSet().members(key);
            //System.err.println(ids);
            if(ids.isEmpty()) continue;
            for(String id : ids){
                Notice notice = mapper.selectById(Integer.parseInt(id));
                sets.add(notice);
            }
        }
        return  new ArrayList<>(sets);

    }

}

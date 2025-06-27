package com.music.demo.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.demo.domain.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

}

package com.music.demo.list.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.demo.domain.entity.MusicList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ListMapper extends BaseMapper<MusicList> {
}

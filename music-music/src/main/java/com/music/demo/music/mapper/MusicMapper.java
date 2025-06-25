package com.music.demo.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.demo.domain.entity.Music;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MusicMapper extends BaseMapper<Music> {
}

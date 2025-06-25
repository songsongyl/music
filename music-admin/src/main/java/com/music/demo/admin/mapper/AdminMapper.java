package com.music.demo.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.music.demo.domain.entity.User;

@Mapper
public interface AdminMapper extends BaseMapper<User> {
}

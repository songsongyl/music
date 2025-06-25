package com.music.demo.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.demo.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

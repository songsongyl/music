package com.music.demo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("user")
public class User {
    public static final String ADMIN = "sfe3";
    public static final String SINGER = "ikp1";
    public static final String USER = "3w1t";

    @TableId(value = "user_id", type = IdType.INPUT)
//    @CreatedBy
    private String id;

    @TableField("user_username")
    private String username;

    @TableField("user_password")
    private String password;

    @TableField("user_name")
    private String name;


    @TableField("user_phone")
    private String phone;

    @TableField("user_avatar")
    private String avatar;

    @TableField("user_updateby")
    private String updateby;

    @TableField("user_update_time")
    private Date updateTime;

    @TableField("user_email")
    private String email;

    @TableField("user_role")
    private String role;
}

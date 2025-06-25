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
@TableName("notice")
public class Notice {

    @TableId(value = "notice_id", type = IdType.AUTO)
    private Integer id;

    @TableField("notice_title")
    private String title;

    @TableField("notice_author")
    private String author;

    @TableField("notice_context")
    private String context;

    @TableField("notice_update_time")
    private Date updateTime;

    @TableField("notice_count")
    private Integer count;
}

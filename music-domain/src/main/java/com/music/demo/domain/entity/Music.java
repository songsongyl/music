package com.music.demo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("music")
public class Music {
    @Schema(description = "音乐主键")
    @TableId(value = "music_id", type = IdType.INPUT)
    private String id;

    @Schema(description = "音乐名称")
    @TableField("music_title")
    private String title;

    @TableField("music_author")
    private String author;

    @TableField("music_lyrics")
    private String lyrics;

    @TableField("music_poster")
    private String poster;

    @TableField("music_poster_sign")
    private String posterSign;

    @Schema(description = "音乐时长")
    @TableField("music_duration")
    private Integer duration;

    @Schema(description = "点击量",defaultValue = "0")
    @TableField("music_count")
    private Integer playCount;

    @TableField("music_description")
    private String description;

    @TableField("music_status")
    private Integer status;

    @TableField("music_collect_count")
    private Integer collectCount;

    @TableField("music_createtime")
    private Date createTime;

    @TableField("music_updatetime")
    private Date updateTime;

    @TableField("music_path")
    private String path;
}

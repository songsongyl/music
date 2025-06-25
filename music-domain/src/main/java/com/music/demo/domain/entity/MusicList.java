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

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TableName("music_list")
public class MusicList {

    @TableId(value = "list_id", type = IdType.INPUT)
    private String id;

    @TableField("list_name")
    private String listName;

    @Schema(description = "点击量",defaultValue = "0")
    @TableField("list_count")
    private Integer listCount;

    @Schema(description = "收藏量",defaultValue = "0")
    @TableField("list_collect_count")
    private Integer listCollectCount;

    @TableField("user_id")
    private String userId;

    @Schema(description = "是否被收藏",defaultValue = "0")
    @TableField("list_status")
    private Integer listStatus;

    // 添加这个用于 JSON 处理
    @TableField("list_musics")
    private String listMusicsJson;

    @TableField(exist = false)
    private List<String> musicIds;
    public void setMusicIds(List<String> musicIds) {
        this.musicIds = musicIds;
        // 手动将 List<Integer> 转换为 JSON 字符串
        this.listMusicsJson = convertMusicIdsToJson(musicIds);
    }

    private String convertMusicIdsToJson(List<String> musicIds) {
        if (musicIds == null || musicIds.isEmpty()) {
            return null;
        }
        // 使用简单的 JSON 转换（建议使用 Jackson 或 Gson 库）
        return "[" + musicIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")) + "]";
    }
//    @TableField(exist = false)
//    private List<Music> listMusics;
}

package com.music.demo.upload.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SmUtil;
import com.music.demo.common.exception.music.MusicException;
import com.music.demo.common.result.HttpResult;
import com.music.demo.domain.entity.Music;
import com.music.demo.music.service.MusicService;
import com.music.demo.upload.service.DocumentService;
import com.music.demo.upload.service.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import java.io.File;

@RestController
@RequestMapping("/api/upload")
@Slf4j
@Tag(name = "文件上传")
public class UploadController {
    @Value("${mypath}")
    private String path;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private MusicService musicService;

    @Operation(summary = "上传图片")
    @PostMapping("/img/{mid}")
    @SneakyThrows
    public HttpResult<String> uploadImg(@RequestPart("file") MultipartFile file,@PathVariable String mid) {
        System.err.println(file.getContentType());  //MIME
        String type = file.getContentType();
        if (!type.contains("image")) {
            throw new MusicException("格式不正确");
        }
//        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String encode = Base64.encode(file.getInputStream());
        String ncode = SmUtil.sm3(encode);
        Music music = musicService.findById(mid);
        music.setPoster(encode);
        music.setPosterSign(ncode);
        musicService.update(music);
//        file.transferTo(new File(path + fileName));
        return HttpResult.success(file.getOriginalFilename());
    }

    @Operation(summary = "上传歌词文件")
    @PostMapping("/lyrics/{mid}")
    @SneakyThrows
    public HttpResult<String> uploadLyrics(@RequestPart("file") MultipartFile file, @PathVariable("mid") String mid) {

//        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//        File saveFile = new File(path + fileName);
//        file.transferTo(saveFile);
        // 提取文本内容
        String content = documentService.extractText(file);
        log.debug(content);
        // 可选：保存文本到数据库
        uploadService.saveLyric(content, mid);
        return HttpResult.success(content);
    }

    @Operation(summary = "上传音频")
    @PostMapping("/audio")
    @SneakyThrows
    public HttpResult<String> uploadAudio(@RequestPart("file") MultipartFile file) {
        // 验证文件类型（可选）
        String contentType = file.getContentType();
        if (contentType == null || !(contentType.startsWith("audio/") ||
                contentType.equals("application/octet-stream"))) {
            throw new MusicException("请上传音频文件");
        }
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        log.debug(fileName);
        File saveFile = new File(path+ fileName);
        file.transferTo(saveFile);//保存文件
        long duration = 0;
            // 使用 JAVE 库获取音频时长
            MultimediaObject multimediaObject = new MultimediaObject(saveFile);
            MultimediaInfo info = multimediaObject.getInfo();
            duration = info.getDuration();
            log.debug("音频时长: " + duration + " 毫秒");
            long totalSeconds = duration / 1000;
            long minutes = totalSeconds / 60;
            long seconds = totalSeconds % 60;
            // 删除临时文件
//            if (tempFile != null && tempFile.exists()) {
//                tempFile.delete();
//            }

        return HttpResult.success(String.format("%d分%02d秒", minutes, seconds));
    }
}
